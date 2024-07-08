package testWalle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

public class AplicacionSEMTest {
	
	private AplicacionSEM app;
	private Integer nroDeCelular;
	private String patente;
	private Modo modoManual;
	private Modo modoAutomatico;
	private EstadoApp estadoAppCaminando;
	private EstadoApp estadoAppManejando;
	private EstadoApp estadoApp;
	private SEM sem;
	private ModoGps gps;
	private Estacionamiento estacionamiento;
	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		nroDeCelular = 1123233232;
		patente = "333SJJ";
		modoAutomatico = spy(ModoAutomatico.class); 
		modoManual = spy(ModoManual.class);
		estadoApp = mock(EstadoApp.class);
		estadoAppManejando = spy(EnAuto.class);
		sem = mock(SEM.class);
		gps = spy(ModoGps.class); 
		estacionamiento = mock(EstacionamientoAplicacion.class);
		usuario = mock(Usuario.class);
		app = new AplicacionSEM(modoAutomatico,estadoAppManejando,sem,usuario,nroDeCelular,gps);
	}
	
	
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoAutomaticamenteYLoFinalizaAutomaticamenteYLaApliacionSEMIniciaYFinalizaElEstacionamiento() {
		
		when(sem.verificarEstacionamientoVigente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigente(nroDeCelular)).thenReturn(false); 
		when(gps.getEstaEncendido()).thenReturn(true);
		when(usuario.getPatente()).thenReturn(patente);
		//Excercise
		app.cargarSaldo(80);
		//app.activarModoAutomatico(); Por defecto esta activado
		app.driving();
		app.walking();
		app.walking();
		app.driving();
		
		assertEquals(80,app.getCredito()); 
		//Descuenta segun cuantas horas estuvo. En este caso, como es en tiempo real,
		//no se podria calcular pero lo que hice fue que le descontara la cantidad maxima de horas que
		//dispone segun su saldo (saldoAcreditado = 80 --> horasMaximas = 2)
		
		//Verify
		verify(modoAutomatico).inicioDeEstacionamiento(app);
		//verify(sem).verificarEstacionamientoVigente(patente);
		verify(modoAutomatico).finDeEstacionamiento(app);
		verify(sem,times(2)).verificarEstacionamientoVigente(nroDeCelular);
		//verify(usuario).alertaDeFinDeEstacionamiento(); ¡Actualmente lo hace por consola!
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoNoVigenteConGPSDesactivadoYModoAutomaticoYLaApliacionSEMConSaldoSuficienteNoIniciaElEstacionamiento()  {
			
		//doThrow(new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento")).when(modoAutomatico).puedeAlertarInicio(app);
		//Excercise
		app.apagarGps();
		app.cargarSaldo(40);
		app.walking();
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
			modoAutomatico.puedeAlertarInicio(app);
	    });
		
		assertEquals("El usuario no esta en una zona de estacionamiento",exception.getMessage());
		
		//Verify
		verify(estadoAppManejando).caminando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app);
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeCaminarAManejarEnUnaZonaEstacionamientoYLaAplicacionSEMEnModoManualDaUnaAlerta() {
		
		//Setup
		
		//Excercise
		app.cargarSaldo(300);
		app.encenderGps();
		app.activarModoManual();
		app.driving();
		app.driving();
		app.driving();
		app.walking();
		
		
		assertEquals(300,app.getCredito());
		
		//Verify
		verify(sem,never()).registrarEstacionamiento(estacionamiento);
		verify(estadoAppManejando).caminando(app);
		verify(estadoAppManejando,times(3)).manejando(app);
		//verify(usuario).alertaInicioDeEstacionamiento(); ¡¡¡Actualmente lo hace por consola!!!
		//Tear Down
		
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualSinSaldoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		//doThrow(new ExcepcionPersonalizada("No hay credito suficiente")).when(modoManual).puedeEstacionar(app, patente);
		
		when(sem.verificarEstacionamientoVigente(patente)).thenReturn(false);
		
		//Excercise
		app.encenderGps();
		app.activarModoManual();
		app.cargarSaldo(20);
		app.iniciarEstacionamiento();
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            app.puedeEstacionar(patente);
		});
		assertEquals("Saldo insuficiente. Estacionamiento no permitido",exception.getMessage());
		
		//Verify
		verify(usuario).getPatente();
		verify(sem).verificarEstacionamientoVigente(patente); //En el orden de ejecucion, esta primera esta condicion
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualConSuAutoYaEstacionadoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
			
		when(usuario.getPatente()).thenReturn(patente);
		when(gps.getEstaEncendido()).thenReturn(true);
		when(sem.verificarEstacionamientoVigente(patente)).thenReturn(false, true);
		//Excercise
		
		//Ya esta activado el gps
		app.activarModoManual(); //Por defecto, esta el modo automatico
		app.cargarSaldo(120);
		app.iniciarEstacionamiento();
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
			app.puedeEstacionar(patente);
			app.puedeEstacionar(patente); //El segundo llamado arroja una excepcion!
		});
		
		assertEquals("Ya hay un estacionamiento vigente con la patente dada",exception.getMessage());
		
		//Recien cuando finaliza el estacionamiento, se cobra la cantidad de horas
		assertEquals(120,app.getCredito()); 
		
		//Verify
		verify(sem,times(2)).verificarEstacionamientoVigente(patente);
		verify(usuario).getPatente();
	}
	
	@Test
	public void testLaAppPreguntaHayEstacionamientoConUnCelular() {
		
		app.hayEstacionamientoCon(nroDeCelular);
		
		verify(sem).verificarEstacionamientoVigente(nroDeCelular);
	}

	@Test
	public void testSaldoDeAppFueraDeFranjaHoraria() {
		
		when(sem.verificarEstacionamientoVigente(nroDeCelular)).thenReturn(false); 
		when(usuario.getPatente()).thenReturn(patente);
		
		//Este test para probarlo hay que cambiar la forma en que se descuenta la plata
		//Osea en vez de que sea en tiempo real, en tiempo fake jajja
		app.cargarSaldo(100);
							
		app.iniciarEstacionamiento();
		
		app.finalizarEstacionamiento();
		
		assertEquals(100,app.getCredito()); //Lo maximo que puede estar son 2 horas
		
		//Verify
		verify(sem,never()).verificarEstacionamientoVigente(patente);
		verify(sem).verificarEstacionamientoVigente(nroDeCelular);
		verify(usuario).getPatente();
		verify(gps,never()).getEstaEncendido();
	}
	@Test
	public void testSaldoDeAppDentroDeFranjaHoraria() {
		//!Quitar codigo comentado cuando sea un horario valido(en vida real jaja)¡
		
		//when(sem.verificarEstacionamientoVigente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigente(nroDeCelular)).thenReturn(false); 
		when(usuario.getPatente()).thenReturn(patente);
		//when(gps.getEstaEncendido()).thenReturn(true);
		
		//Este test para probarlo hay que cambiar la forma en que se descuenta la plata
		//Osea en vez de que sea en tiempo real, en tiempo fake jajja
		app.cargarSaldo(100);
							
		app.iniciarEstacionamiento();
		
		app.finalizarEstacionamiento();
		
		assertEquals(100,app.getCredito()); //Lo maximo que puede estar son 2 horas
		
		//Verify
		//verify(sem).verificarEstacionamientoVigente(patente);
		verify(sem).verificarEstacionamientoVigente(nroDeCelular);
		verify(usuario).getPatente();
		//verify(gps).getEstaEncendido();
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoSinSaldoEnSuAplicacionEnModoManual() {
		
		when(gps.getEstaEncendido()).thenReturn(true);
		//when(modo.estaEnModoAutomatico()).thenReturn(false);

		app.encenderGps();
		app.activarModoAutomatico();
		app.iniciarEstacionamiento();
		
		assertEquals(app.consultarSaldo(),0); //No queda saldo negativo
		
		app.cargarSaldo(120);
		
		app.iniciarEstacionamiento(); //Ahora si puede iniciarse
		
		
		//Verify
		
		
	}
	@Test
	public void testLaAplicacionEsConsultadaPorSuSaldo() {
		
		assertEquals(app.consultarSaldo(),0);
		
		app.cargarSaldo(100);
		
		assertEquals(app.consultarSaldo(),100);
	}
	
	@Test
	public void testAppCalculaElCredito() {
		
		app.cargarSaldo(130);
		
		assertEquals(app.cantidadDeHorasSegunSaldo(),3);
		
	}
	
	@Test
	public void getUsuario() {
		//verify
		assertEquals(app.getUsuario(),usuario);
		
	}
	
	@Test
	public void getSistemaEstacionamiento() {
		//verify
		assertEquals(app.getSistemaEstacionamiento(), sem);
		
	}


}

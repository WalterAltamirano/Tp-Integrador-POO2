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
	private ModoGps gpsActivado;
	private Estacionamiento estacionamiento;
	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		nroDeCelular = 1123233232;
		patente = "333SJJ";
		modoAutomatico = spy(ModoAutomatico.class); 
		modoManual = spy(ModoManual.class);
		estadoApp = mock(EstadoApp.class);
		estadoAppCaminando = spy(Caminando.class);
		estadoAppManejando = spy(EnAuto.class);
		sem = mock(SEM.class);
		gpsActivado = spy(ModoGps.class); 
		estacionamiento = mock(EstacionamientoAplicacion.class);
		usuario = mock(Usuario.class);
		app = new AplicacionSEM(modoAutomatico,estadoAppManejando,sem,usuario,nroDeCelular,gpsActivado);
	}
	
	
	@Test
	public void testUnUsuarioPasaDeCaminandoAManejandoEnUnaZonaDeEstacionameintoConGPSActivadoYModoAutomaticoYLaApliacionSEMFinalizaElEstacionamiento() {
		
		when(sem.verificarEstacionamientoVigente(nroDeCelular)).thenReturn(true);
		
		//Excercise
		app.encenderGps();
		//app.activarModoAutomatico(); Por defecto esta activado
		app.walking();
		app.driving();
		
		//Verify
		verify(modoAutomatico).finDeEstacionamiento(app);
		verify(sem).verificarEstacionamientoVigente(nroDeCelular);
		//verify(usuario).alertaDeFinDeEstacionamiento(); ¡Actualmente lo hace por consola!
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoNoVigenteConGPSDesactivadoYModoAutomaticoYLaApliacionSEMConSaldoSuficienteNoIniciaElEstacionamiento()  {
			
		
		//Excercise
		app.apagarGps();
		app.cargarSaldo(40);
		app.walking();
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
	            app.puedeEstacionar(patente);
	    });
		
		assertEquals("El usuario no esta en una zona de estacionamiento",exception.getMessage());
		//Buscar forma de captar excepcion con un metodo que no retorna nada (void)
		
		//Verify
		verify(estadoAppManejando).caminando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app);
		
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeCaminarAManejarEnUnaZonaEstacionamientoYLaAplicacionSEMEnModoManualDaUnaAlerta() {
		
		//Setup
		
		//Excercise
		app.encenderGps();
		app.activarModoManual();
		app.driving();
		app.driving();
		app.driving();
		app.walking();
		
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
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualConSuAutoYaEstacionadoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		
		
		when(usuario.getPatente()).thenReturn(patente);
		when(gpsActivado.getEstaEncendido()).thenReturn(true);
		when(sem.verificarEstacionamientoVigente(patente)).thenReturn(false, true);
		
		//Excercise
		
		//Ya esta activado el gps
		app.activarModoManual(); //Por defecto, esta el modo automatico
		app.cargarSaldo(120);
		app.iniciarEstacionamiento();
		app.iniciarEstacionamiento();
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            app.puedeEstacionar(patente);
            app.puedeEstacionar(patente);
		});
		
		assertEquals("Ya hay un estacionamiento vigente con la patente dada",exception.getMessage());
		
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
	public void testSaldoDeApp() {
		app.cargarSaldo(100);
		
		app.iniciarEstacionamiento();
		
		app.finalizarEstacionamiento();
		
		assertEquals(20,app.getCredito()); //Lo maximo que puede estar son 2 horas
		
		//Verify
		
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoSinSaldoEnSuAplicacionEnModoManual() {
		
		when(gpsActivado.getEstaEncendido()).thenReturn(true);
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

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
	//private ModoGps gps;
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
		//gps = spy(ModoGps.class); 
		estacionamiento = mock(EstacionamientoAplicacion.class);
		usuario = mock(Usuario.class);
		app = new AplicacionSEM(modoAutomatico,estadoAppManejando,sem,usuario,nroDeCelular,true);
	}
	
	
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoAutomaticamenteYLoFinalizaAutomaticamenteYLaApliacionSEMIniciaYFinalizaElEstacionamiento() {
		
		//Harcodeo la hora antes para probar el test--
		//Si no es una hora valida, va a fallar el test 
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(true); 
		when(usuario.getPatente()).thenReturn(patente);
		//Excercise
		app.setHoraInicio(13);
		app.setHoraFin(15);   //Harcodeo la hora
		app.cargarSaldo(80);
		//app.activarModoAutomatico(); Por defecto esta activado
		app.driving();
		app.walking(); //PasoACaminando
		app.walking();
		app.driving(); //PasoAAuto
		
		assertEquals(0,app.consultarSaldo()); //Se descuentan las 2 horas
	
		
		//Verify
		verify(estadoAppManejando).caminando(app);
		verify(estadoAppManejando).manejando(app);
		verify(sem).verificarEstacionamientoVigentePorPatente(patente);
		verify(modoAutomatico).inicioDeEstacionamiento(app);
		verify(modoAutomatico).finDeEstacionamiento(app);
		verify(sem).verificarEstacionamientoVigentePorCelular(nroDeCelular);
		//verify(usuario).alertaDeFinDeEstacionamiento(); ¡Actualmente lo hace por consola!
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoNoVigenteConGPSDesactivadoYModoAutomaticoYLaApliacionSEMConSaldoSuficienteNoIniciaElEstacionamiento()  {
			
		//doThrow(new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento")).when(modoAutomatico).puedeAlertarInicio(app);
		
		//Excercise
		app.setHoraInicio(16);
		app.apagarGps(); //Por defecto esta encendido
		app.cargarSaldo(40);
		app.walking(); //Por defecto la app esta en Modo Automatico
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
			app.puedeEstacionar(patente);
	    });
		
		assertEquals("El usuario no esta en una zona de estacionamiento",exception.getMessage());
		
		//Verify
		verify(estadoAppManejando).caminando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app);
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeCaminarAManejarEnUnaZonaEstacionamientoYLaAplicacionSEMEnModoManualDaUnaAlerta() {
		
		
		//Excercise
		app.cargarSaldo(300);
		app.encenderGps();
		app.activarModoManual();
		app.driving();
		app.driving();
		app.driving();
		app.walking(); //Al estar en Modo Manual, no se inicia
		
		
		assertEquals(300,app.consultarSaldo()); //No se deberia descontar nada
		
		//Verify
		verify(sem,never()).verificarEstacionamientoVigentePorPatente(patente);
		verify(estadoAppManejando).caminando(app);
		verify(estadoAppManejando,times(3)).manejando(app);
		//verify(usuario).alertaInicioDeEstacionamiento(); ¡¡¡Actualmente lo hace por consola!!!
		
		//Tear Down
		
		 
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualSinSaldoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		//doThrow(new ExcepcionPersonalizada("No hay credito suficiente")).when(modoManual).puedeEstacionar(app, patente);
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		
		//Excercise
		app.setHoraInicio(16);
		app.encenderGps();
		app.activarModoManual();
		app.cargarSaldo(20);
		app.iniciarEstacionamiento();
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            app.puedeEstacionar(patente);
		});
		assertEquals("Saldo insuficiente. Estacionamiento no permitido",exception.getMessage());
		
		//Verify
		//verify(usuario,times(1)).getPatente();falla si no lo comento
		verify(sem).verificarEstacionamientoVigentePorPatente(patente);
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualConSuAutoYaEstacionadoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
			
		when(usuario.getPatente()).thenReturn(patente);
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false, true);
		//Excercise
		app.setHoraInicio(16);
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
		assertEquals(120,app.consultarSaldo()); 
		
		//Verify
		verify(sem,times(2)).verificarEstacionamientoVigentePorPatente(patente);
		//verify(usuario).getPatente(); falla si no lo comento
	}

	@Test
	public void testSaldoDeAppFueraDeFranjaHoraria() {
		
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(false); 
		when(usuario.getPatente()).thenReturn(patente);
		
		app.setHoraInicio(22);
		//Este test para probarlo hay que cambiar la forma en que se descuenta la plata
		//Osea en vez de que sea en tiempo real, en tiempo fake jajja
		app.cargarSaldo(100);
							
		app.iniciarEstacionamiento();
		
		app.finalizarEstacionamiento();
		
		assertEquals(100,app.consultarSaldo()); //Lo maximo que puede estar son 2 horas
		
		//Verify
		verify(sem,never()).verificarEstacionamientoVigentePorPatente(patente);
		verify(sem).verificarEstacionamientoVigentePorCelular(nroDeCelular);
		//verify(usuario,times(2)).getPatente();falla si no lo comento
	}
	@Test
	public void testSaldoDeAppDentroDeFranjaHoraria() {
		//!Quitar codigo comentado cuando sea un horario valido(en vida real jaja)¡
		
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(true); 
		when(usuario.getPatente()).thenReturn(patente);
		
		//Este test para probarlo hay que cambiar la forma en que se descuenta la plata
		//Osea en vez de que sea en tiempo real, en tiempo fake jajja
		app.cargarSaldo(100);
		app.setHoraInicio(16);
		app.setHoraFin(18);
		app.iniciarEstacionamiento();
		app.finalizarEstacionamiento();
		
		assertEquals(20,app.consultarSaldo()); //Lo maximo que puede estar son 2 horas 
	
		
		//Verify
		verify(sem).verificarEstacionamientoVigentePorPatente(patente);
		verify(sem).verificarEstacionamientoVigentePorCelular(nroDeCelular);
		verify(usuario,times(2)).getPatente();
		
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoSinSaldoEnSuAplicacionEnModoManualCargaSaldoEIniciaSuEstacionamieto() {
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		when(usuario.getPatente()).thenReturn(patente);
		//Excercise
		app.encenderGps();
		app.activarModoManual();
		
		app.setHoraInicio(16);
		
		app.iniciarEstacionamiento();
		
		assertEquals(app.consultarSaldo(),0); //No tiene saldo
		
		app.cargarSaldo(120); 
		
		app.setHoraInicio(16);
		
		app.iniciarEstacionamiento(); //Ahora si puede iniciarse
		
		//Verify
		//verify(usuario,times(2)).getPatente();
		verify(sem,times(2)).verificarEstacionamientoVigentePorPatente(patente);
		
	}

}
 
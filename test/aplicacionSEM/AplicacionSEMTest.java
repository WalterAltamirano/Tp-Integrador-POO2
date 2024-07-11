package aplicacionSEM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import dataUsuario.Usuario;
import estacionamiento.Estacionamiento;
import estacionamiento.EstacionamientoAplicacion;
import estadosAplicacion.EnAuto;
import estadosAplicacion.EstadoApp;
import estrategiasAplicacion.Modo;
import estrategiasAplicacion.ModoAutomatico;
import estrategiasAplicacion.ModoManual;
import sistemaEstacionamientoMunicipal.SEM;

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
	private ModoAutomatico modoAutomatico;
	private EstadoApp estadoAppCaminando;
	private EstadoApp estadoAppManejando;
	private EstadoApp estadoApp;
	private SEM sem;
	
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
		sem = spy(SEM.class);
		
		estacionamiento = mock(EstacionamientoAplicacion.class);
		usuario = mock(Usuario.class);
		app = new AplicacionSEM(modoAutomatico,estadoAppManejando,sem,usuario,nroDeCelular,true);
	}
	
	
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoAutomaticamenteYLoFinalizaAutomaticamenteYLaApliacionSEMIniciaYFinalizaElEstacionamiento() {
		
	
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(true); 
		when(usuario.getPatente()).thenReturn(patente);
		when(modoAutomatico.calcularHoraActual()).thenReturn(16);
	
		app.cargarSaldo(80);
		
		app.driving();
		app.walking(); //PasoACaminando
		app.walking();
		app.driving(); //PasoAAuto
		

		
		//Verify
		verify(estadoAppManejando).caminando(app);
		verify(estadoAppManejando).manejando(app);
		verify(sem).verificarEstacionamientoVigentePorPatente(patente);
		verify(modoAutomatico).inicioDeEstacionamiento(app);
		verify(modoAutomatico).finDeEstacionamiento(app);
		verify(sem).verificarEstacionamientoVigentePorCelular(nroDeCelular);
		
	}
	@Test
	public void testUnUsuarioIniciaUnEstacionamientoNoVigenteConGPSDesactivadoYModoAutomaticoYLaApliacionSEMConSaldoSuficienteNoIniciaElEstacionamiento()  {
			
		
		
		//Excercise
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
		
		
		//Tear Down
		
		 
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualSinSaldoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		
		//Excercise
		app.encenderGps();
		app.activarModoManual();
		app.cargarSaldo(20);
		app.iniciarEstacionamiento(16); //Horita
		
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
            app.puedeEstacionar(patente);
		});
		assertEquals("Saldo insuficiente. Estacionamiento no permitido",exception.getMessage());
		
		//Verify
		
		verify(sem).verificarEstacionamientoVigentePorPatente(patente);
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualConSuAutoYaEstacionadoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
			
		when(usuario.getPatente()).thenReturn(patente);
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false, true);
		//Excercise
		
		app.activarModoManual(); //Por defecto, esta el modo automatico
		app.cargarSaldo(120);
		app.iniciarEstacionamiento(16);
		Throwable exception = assertThrows(ExcepcionPersonalizada.class, () -> {
			app.puedeEstacionar(patente);
			app.puedeEstacionar(patente); //El segundo llamado arroja una excepcion!
		});
		
		assertEquals("Ya hay un estacionamiento vigente con la patente dada",exception.getMessage());
		
		//Recien cuando finaliza el estacionamiento, se cobra la cantidad de horas
		assertEquals(120,app.consultarSaldo()); 
		
		//Verify
		verify(sem,times(2)).verificarEstacionamientoVigentePorPatente(patente);
		
	}

	@Test
	public void testSaldoDeAppFueraDeFranjaHoraria() {
		
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(false); 
		when(usuario.getPatente()).thenReturn(patente);
		
		
		app.cargarSaldo(100);
							
		app.iniciarEstacionamiento(21);
		
		app.finalizarEstacionamiento();
		
		assertEquals(100,app.consultarSaldo()); //Lo maximo que puede estar son 2 horas
		
		//Verify
		verify(sem,never()).verificarEstacionamientoVigentePorPatente(patente);
		verify(sem).verificarEstacionamientoVigentePorCelular(nroDeCelular);
		
	}
	@Test
	public void testSaldoDeAppDentroDeFranjaHoraria() {
		
		
		when(sem.verificarEstacionamientoVigentePorPatente(patente)).thenReturn(false);
		when(sem.verificarEstacionamientoVigentePorCelular(nroDeCelular)).thenReturn(true); 
		when(usuario.getPatente()).thenReturn(patente);
		when(estacionamiento.getHoraInicio()).thenReturn(16);
		when(estacionamiento.getHoraFin()).thenReturn(18);
		
		
		app.activarModoManual();
		app.cargarSaldo(100);
		
		app.iniciarEstacionamiento(16);
		app.finalizarEstacionamiento();
		app.descontarSaldo(estacionamiento);
		
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
		
		
		app.iniciarEstacionamiento(16);
		
		assertEquals(app.consultarSaldo(),0); //No tiene saldo
		
		app.cargarSaldo(120); 
		
		
		app.iniciarEstacionamiento(16); //Ahora si puede iniciarse
		
		//Verify
		
		verify(sem,times(2)).verificarEstacionamientoVigentePorPatente(patente);
		
	}

}
 
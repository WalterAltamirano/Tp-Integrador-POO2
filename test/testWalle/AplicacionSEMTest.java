package testWalle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

public class AplicacionSEMTest {
	
	private AplicacionSEM app;
	private int nroDeCelular;
	private Auto automovil;
	//private Modo modoManual;
	private Modo modo;
	private EstadoApp estadoAplicacion;
	private SEM sistemaEstacionamiento;
	private Encendido gps;
	private Estacionamiento estacionamiento;
	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		nroDeCelular = 1123233232;
		automovil = mock(Auto.class);
		modo = mock(Modo.class); 
		estadoAplicacion = mock(EnAuto.class);
		sistemaEstacionamiento = mock(SEM.class);
		gps = mock(Encendido.class);
		estacionamiento = mock(Estacionamiento.class);
		usuario = mock(Usuario.class);
		app = new AplicacionSEM(modo,estadoAplicacion,sistemaEstacionamiento,usuario,nroDeCelular);
	}
	
	
	@Test
	public void testUnUsuarioDejaSuAutoEnUnaZonaDeEstacionameintoConGPSActivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() throws Exception {
		
		//SetUp
		
		//Excercise
		app.cargarSaldo(100);
		app.driving();
		app.walking(); //Se activaria solo el incio de estacionamiento
		
		assertEquals(100,app.consultarSaldo()); //No se descuenta el saldo
		
		//Verify
		verify(estadoAplicacion).manejando(app);
		
		//Tear Down
	
		
	}
	@Test
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSDesactivadoYModoAutomaticoYLaApliacionSEMNoIniciaElEstacionamiento() throws Exception {
		//SetUp
		
		
		//Excercise
				
		app.elegirEstadoGPS(gps);
		app.elegirModo(modo);
		app.cargarSaldo(100);
		app.driving();
		app.walking();
				
		//Verify
		verify(sistemaEstacionamiento,never()).registrarEstacionamiento(estacionamiento);
		verify(estadoAplicacion).manejando(app);
		verify(modo,never()).inicioDeEstacionamiento(app,nroDeCelular,"333ALO");
				
				
		//Tear Down
			
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeCaminarAManejarEnUnaZonaEstacionamientoYLaAplicacionSEMEnModoManualYDaUnaAlerta() {
		
		//Setup
		when(gps.estaEncendido()).thenReturn(true);
		when(modo.estaEnModoAutomatico()).thenReturn(false);
		
		//Excercise
		app.elegirEstadoGPS(gps);
		app.cargarSaldo(120);
		app.driving();
		app.driving();
		app.walking();
		//Verify
		verify(sistemaEstacionamiento,never()).registrarEstacionamiento(estacionamiento);
		//Tear Down
		
		
	}
	@Test
	public void testModoRecibeUnInciaUnEstacionamiento() {
		
		//SetUp
		
		//Excercise
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
		
		//Verify
		verify(modo).inicioDeEstacionamiento(app, nroDeCelular, "333ALO");
		//Tear down
	}
	@Test
	public void testModoRecibeUnFinDeEstacionamiento() {
		
		//SetUp
		
		//Excercise
		app.finalizarEstacionamiento(nroDeCelular);
		
		//Verify
		verify(modo).finDeEstacionamiento(app, nroDeCelular);
		//Tear down
	}
	@Test
	public void testNoficiarAlertaIncio() {
		
		app.alertaInicioDeEstacionamiento();
		
		//
		verify(modo).notificarAlertaDeInicioDeEstacionamiento(app);
		
	}
	@Test
	public void testNotificacionAlertaFin() {
		
		app.alertaFinDeEstacionamiento();
		
		//
		verify(modo).notificarAlertaDeFinDeEstacionamiento(app);
		
	}
	
	
	@Test
	public void testAppCambiaDeModoYDaUnAvisoDeCambio() {
		
		app.elegirModo(modo);
		
		verify(modo).avisoDeCambio();
		
	}
	
	@Test
	public void testLaAppPreguntaHayEstacionamientoConUnaPatente() {
		
		app.hayEstacionamientoCon("333ALO");
		
		verify(sistemaEstacionamiento).verificarEstacionamientoVigente("333ALO");
	}
	
	@Test
	public void testLaAppPreguntaHayEstacionamientoConUnCelular() {
		
		app.hayEstacionamientoCon(nroDeCelular);
		
		verify(sistemaEstacionamiento).verificarEstacionamientoVigente(nroDeCelular);
	}
	@Test
	public void testAppVerificaQueTengaSuficienteSaldo() {
		
		app.cargarSaldo(100);
		
		assertTrue(app.tieneCreditoSuficienteParaEstacionar());
		
		
	}
	@Test
	public void testSaldoDeApp() {
		app.cargarSaldo(100);
		
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
		
		app.finalizarEstacionamiento(nroDeCelular);
		
		app.descontarSaldo();
		
		assertEquals(app.getCredito(),20); //Lo maximo que puede estar son 2 horas
		
		//Verify
		
	}
	@Test 
	public void testUsuarioElijeElModoAutomaticoYIniciaEstacionamientoYLaAplicacionSEMVerficaQueElGpsEsteEncendido() {
		
		when(gps.estaEncendido()).thenReturn(true);
		
		app.elegirEstadoGPS(gps);
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
		
		assertTrue(app.estaEnZonaDeEstacionamiento());
		
		//Verify
		verify(gps).estaEncendido();
		
	}
	@Test
	public void testAplicacioIniciaEstacionamiento() {
		
		when(gps.estaEncendido()).thenReturn(true);
		when(modo.estaEnModoAutomatico()).thenReturn(false);

		app.elegirEstadoGPS(gps);
		app.elegirModo(modo);
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
		
		assertEquals(app.consultarSaldo(),0); //No queda saldo negativo
		
		app.cargarSaldo(120);
		
		app.inicioEstacionamiento(nroDeCelular, "333ALO"); //Ahora si puede iniciarse
		
		app.finalizarEstacionamiento(nroDeCelular);
		//Verify
		verify(modo,times(2)).inicioDeEstacionamiento(app,nroDeCelular,"333ALO");
		verify(modo).finDeEstacionamiento(app,nroDeCelular);
		
		
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
	public void testAsignarCelular() {
		
		app.asignarCelular(nroDeCelular);
		
		assertEquals(app.getNumeroDeCelular(),nroDeCelular);
		
	}
	
	@Test
	public void setEstado() {
		
		app.setEstado(estadoAplicacion);
		
		assertEquals(app.getEstado(),estadoAplicacion);
		
	}


}

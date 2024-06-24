package testWalle;

import org.junit.Before;
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
	private Integer nroDeCelular;
	private String patente;
	//private Modo modoManual;
	private Modo modoAutomatico;
	private EstadoApp estadoAplicacion;
	private SEM sem;
	private EstadoGPS gps;
	private Estacionamiento estacionamiento;
	private Usuario usuario;
	private Modo modoManual;
	
	@BeforeEach
	public void setUp() {
		nroDeCelular = 1123233232;
		patente = "333SJJ";
		modoAutomatico = spy(ModoAutomatico.class); 
		modoManual = spy(ModoManual.class);
		estadoAplicacion = mock(EnAuto.class);
		sem = mock(SEM.class);
		gps = mock(EstadoGPS.class);
		estacionamiento = mock(EstacionamientoAplicacion.class);
		usuario = mock(Usuario.class);
		
		app = new AplicacionSEM(modoAutomatico,estadoAplicacion,sem,usuario,nroDeCelular,gps);
	}
	
	
	@Test
	public void testUnUsuarioDejaSuAutoEnUnaZonaDeEstacionameintoConGPSActivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() {
		
		
		app.prenderGps();
		app.elegirModo(modoAutomatico);
		app.cargarSaldo(100);
		app.driving();
		app.walking(); 
		app.inicioEstacionamiento(nroDeCelular, patente);
		
		assertEquals(100,app.consultarSaldo()); 
		
		verify(estadoAplicacion).manejando(app);
		verify(estadoAplicacion).caminando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app, nroDeCelular, patente);
		verify(modoAutomatico).avisoDeCambio();
		
	}
	@Test
	public void testUnUsuarioFinalizaUnEstacionamientoConGPSDesactivadoYModoAutomaticoYLaApliacionSEMNoIniciaElEstacionamiento()  {
			
		
		app.apagarGps();
		app.finalizarEstacionamiento(nroDeCelular);		
		
		verify(modoAutomatico).finDeEstacionamiento(app, nroDeCelular);		
		
		
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeCaminarAManejarEnUnaZonaEstacionamientoYLaAplicacionSEMEnModoManualYDaUnaAlerta() {
		
		//Setup
		when(gps.getEstaEncendido()).thenReturn(true);
		//when(modo.estaEnModoAutomatico()).thenReturn(false);
		
		//Excercise
		app.apagarGps();
		app.cargarSaldo(120);
		app.driving();
		app.driving();
		app.walking();
		//Verify
		verify(sem,never()).registrarEstacionamiento(estacionamiento);
		//Tear Down
		
		
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualSinSaldoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		doThrow(new ExcepcionPersonalizada("No hay credito suficiente")).when(modoManual).puedeEstacionar(app, patente);
		
		app.elegirModo(modoManual);
		app.cargarSaldo(20);
		app.inicioEstacionamiento(nroDeCelular, patente);
		
		verify(modoManual).inicioDeEstacionamiento(app, nroDeCelular, patente);
		verify(modoManual).puedeEstacionar(app, patente);
		verify(modoManual).avisoDeCambio();
	}
	@Test
	public void testUsuarioIniciaUnEstacionamientoConGpsActivadoYModoManualConSuAutoYaEstacionadoYLaAplicacionSEMArrojaUnaExcepcion() throws ExcepcionPersonalizada{
		
		
		when(sem.verificarEstacionamientoVigente(patente)).thenReturn(true);
		when(gps.getEstaEncendido()).thenReturn(true);
		
		app.prenderGps();
		app.elegirModo(modoManual);
		app.cargarSaldo(100);
		app.inicioEstacionamiento(nroDeCelular, patente);
		
		verify(modoManual).inicioDeEstacionamiento(app, nroDeCelular, patente);
		verify(modoManual).puedeEstacionar(app, patente);
		verify(modoManual).avisoDeCambio();
		verify(sem).verificarEstacionamientoVigente(patente);
	}
	@Test
	public void testNoficiarAlertaIncio() {
		
		app.alertaInicioDeEstacionamiento();
		
		//
		verify(modoAutomatico).notificarAlertaDeInicioDeEstacionamiento(app);
		
	}
	@Test
	public void testNotificacionAlertaFin() {
		
		app.alertaFinDeEstacionamiento();
		
		//
		verify(modoAutomatico).notificarAlertaDeFinDeEstacionamiento(app);
		
	}
	
	
	@Test
	public void testLaAppPreguntaHayEstacionamientoConUnaPatente() {
		
		app.hayEstacionamientoCon("333ALO");
		
		verify(sem).verificarEstacionamientoVigente("333ALO");
	}
	
	@Test
	public void testLaAppPreguntaHayEstacionamientoConUnCelular() {
		
		app.hayEstacionamientoCon(nroDeCelular);
		
		verify(sem).verificarEstacionamientoVigente(nroDeCelular);
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
		
		when(gps.getEstaEncendido()).thenReturn(true);
		
		app.prenderGps();
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
			
		//Verify
		verify(gps).getEstaEncendido();
		
	}
	@Test
	public void testAplicacioIniciaEstacionamiento() {
		
		when(gps.getEstaEncendido()).thenReturn(true);
		//when(modo.estaEnModoAutomatico()).thenReturn(false);

		app.prenderGps();
		app.elegirModo(modoAutomatico);
		app.inicioEstacionamiento(nroDeCelular, "333ALO");
		
		assertEquals(app.consultarSaldo(),0); //No queda saldo negativo
		
		app.cargarSaldo(120);
		
		app.inicioEstacionamiento(nroDeCelular, "333ALO"); //Ahora si puede iniciarse
		
		app.finalizarEstacionamiento(nroDeCelular);
		//Verify
		verify(modoAutomatico,times(2)).inicioDeEstacionamiento(app,nroDeCelular,"333ALO");
		verify(modoAutomatico).finDeEstacionamiento(app,nroDeCelular);
		
		
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


}

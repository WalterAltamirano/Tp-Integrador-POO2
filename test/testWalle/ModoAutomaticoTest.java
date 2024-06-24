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

public class ModoAutomaticoTest {
	
	private Modo modo;
	private AplicacionSEM app;
	private int nroDeCelular;
	private String patente;
	private Estacionamiento estacionamiento;
	private SEM sistema;
	private EstadoGPS gps;
	
	@BeforeEach
	public void setUp() {

		 app = mock(AplicacionSEM.class);
		 nroDeCelular = 1123234444;
		 patente = "444JEO";
		 sistema = mock(SEM.class);
		 estacionamiento = mock(EstacionamientoAplicacion.class);
		 gps = mock(EstadoGPS.class);
		 modo= new ModoAutomatico();
	}
	
	@Test
	public void testAplicacionRecibeUnInicioDeEstacionamientoYDelegaEnModoUnInicioDeEstacionamientoPeroNoSeInicia() {
		
		//SetUp
		when(app.consultarSaldo()).thenReturn(20d);
		
		//Excercise
		modo.inicioDeEstacionamiento(app, 1123234444, patente);
		
		//Verify
		verify(sistema,never()).registrarEstacionamiento(estacionamiento); //No se cumple alguna condicion
		verify(app).hayEstacionamientoCon(patente);
		verify(app).tieneCreditoSuficienteParaEstacionar();
		
	}
	@Test
	public void testAplicacionRecibeUnInicioDeEstacionamientoYDelegaEnModoUnInicioDeEstacionamientoYSeInicia() {
		
		//SetUp
		when(app.getCredito()).thenReturn(100d);
		when(app.tieneCreditoSuficienteParaEstacionar()).thenReturn(true);
		when(app.hayEstacionamientoCon(patente)).thenReturn(false);
		when(app.estaEnZonaDeEstacionamiento()).thenReturn(true);
		when(app.getSistemaEstacionamiento()).thenReturn(sistema);
		when(app.getGps()).thenReturn(gps);
		//Excercise
		modo.inicioDeEstacionamiento(app, 1123234444, patente);
		//Verify
		verify(app).getHoraInicio();
		
	}
	@Test
	public void testAplicacionRecibeUnFinDeEstacionamientoYDelegaEnModoUnFinDeEstacionamientoYSeFinaliza() {
		
		//SetUp
		when(app.getCredito()).thenReturn(100d);
		when(app.tieneCreditoSuficienteParaEstacionar()).thenReturn(true);
		when(app.hayEstacionamientoCon(patente)).thenReturn(false);
		when(app.estaEnZonaDeEstacionamiento()).thenReturn(true);
		when(app.getSistemaEstacionamiento()).thenReturn(sistema);
		when(app.getGps()).thenReturn(gps);
		when(estacionamiento.getPatente()).thenReturn(patente);
		//Excercise
		modo.inicioDeEstacionamiento(app, nroDeCelular, patente);
		modo.finDeEstacionamiento(app, 1123234444);
		//Verify
		verify(app,atLeast(3)).getHoraInicio();
		verify(app,atLeast(2)).getSistemaEstacionamiento();
		
	}
	@Test
	public void testAplicacionRecibeUnFinDeEstacionamientoYDelegaEnModoUnFinDeEstacionamientoPeroNoSeFinaliza() {
		
		//SetUp
		when(app.consultarSaldo()).thenReturn(20d);
		when(app.getSistemaEstacionamiento()).thenReturn(sistema);
		when(app.getGps()).thenReturn(gps);
		//Excercise
		modo.finDeEstacionamiento(app, 1123234444);
		
		//Verify
		verify(sistema,never()).registrarEstacionamiento(estacionamiento); //No se cumple alguna condicion
		
	}
	
	@Test
	public void testAplicacionRecibeUnaAlertaDeInicioDeEstacionamientoYDelegaEnModoUnaNotificacionDeInicio() {
		
		//SetUp
		when(app.getGps()).thenReturn(gps);
		when(gps.getEstaEncendido()).thenReturn(true);
		//Excercise
		modo.notificarAlertaDeInicioDeEstacionamiento(app);
		
		//Verify
		verify(app).getGps();
		verify(gps).getEstaEncendido();
	}
	@Test
	public void testAplicacionRecibeUnaAlertaDeFinDeEstacionamientoYDelegaEnModoUnaNotificacionDeFin() {
		
		//SetUp
		when(app.getGps()).thenReturn(gps);
		when(gps.getEstaEncendido()).thenReturn(true);
		//Excercise
		modo.notificarAlertaDeFinDeEstacionamiento(app);
		
		//Verify
		verify(app).getGps();
		verify(gps).getEstaEncendido();
	}
	@Test
	public void testModoDaUnaRespuestaInicial() {
		
		//SetUp
		
		//Excercise
		modo.darRespuestaInicial(app);
		//Verify
		verify(app).getHoraInicio();
	}
	@Test
	public void testModoDaUnaRespuestaFinal() {
		
		//SetUp
		
		//Excercise
		modo.darRespuestaFinal(app);
		//Verify
		verify(app,atLeast(2)).getHoraInicio();
		verify(app).calcularCreditoAPagar();
	}
	
//	@Test
//	public void testEstaEnModoAutomatico() {
//		
//		assertTrue(modo.estaEnModoAutomatico());
//	}
	@Test
	public void testPruebaAvisoDeCambio() {
		modo.avisoDeCambio();
	}
	@Test
	public void test() {
		
	}
}

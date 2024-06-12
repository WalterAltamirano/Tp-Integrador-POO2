package testWalle;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

public class AplicacionSEMTest {
	
	
	@BeforeEach
	public void setUp() {
		
	}
	
	
	@Test
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSActivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() {
		
		//SetUp
		int nroDeCelular = 1123233232;
		LocalDateTime horaDeInicio = mock(LocalDateTime.class);
		LocalDateTime horaDeFinalizacion = mock(LocalDateTime.class);
		Auto automovil = mock(Auto.class);
		Modo modoManual = mock(Modo.class); 
		Modo modoAutomatico= mock(Modo.class); 
		EstadoApp manejando = mock(EstadoApp.class);
		SEM sistemaEstacionamiento = mock(SEM.class);
		EstadoGPS gpsEncendido = mock(EstadoGPS.class);
		Estacionamiento estacionamiento = mock(Estacionamiento.class);
		Usuario usuario = mock(Usuario.class);
		
		
		clasesWalle.AplicacionSEM app = new clasesWalle.AplicacionSEM(modoManual,manejando,sistemaEstacionamiento,usuario,nroDeCelular,horaDeInicio,horaDeFinalizacion);
		
		//when(usuario.patenteDelAuto()).thenReturn("333ALO");
		when(gpsEncendido.estaEncendido()).thenReturn(true);
		when(sistemaEstacionamiento.verificarEstacionamientoVigente("333ALO")).thenReturn(false);
		when(automovil.getPatente()).thenReturn("333ALO");
		
		//Excercise
		
		app.elegirEstadoGPS(gpsEncendido);
		app.elegirModo(modoAutomatico);
		app.cargarSaldo(100);
		app.driving();
		app.walking(); //Se activaria solo el incio de estacionamiento
		//app.inicioEstacionamiento(1123233232, "333ALO");
		
		assertEquals(100,app.consultarSaldo()); //No se descuenta el saldo
		
		//Verify
		verify(sistemaEstacionamiento).registrarEstacionamiento(estacionamiento);
		verify(manejando).manejando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app,nroDeCelular,"333ALO");
		
		//Tear Down
	
		
	}
	@Test
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSDesactivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() {
		
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeManejarACaminarEnUnaZonaEstacionamientoYLaAplicacionSEMAlertaAlUsuario() {
		
		
	}
	@Test
	public void test4() {
		
	}
}

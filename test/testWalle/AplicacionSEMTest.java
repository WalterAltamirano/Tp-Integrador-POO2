package testWalle;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import clasesIan.Auto;
import clasesIan.Estacionamiento;
import clasesIan.EstadoApp;
import clasesIan.Usuario;
import clasesMatias.SEM;
import clasesWalle.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AplicacionSEMTest {
	
	
	@BeforeEach
	public void setUp() {
		
	}
	
	
	@Test
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSActivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() {
		
		//SetUp
		int horaActual = 10;
		int nroDeCelular = 1123233232;
		Auto automovil = mock(Auto.class);
		Modo modoManual = mock(ModoManual.class); 
		Modo modoAutomatico= mock(Modo.class); 
		EstadoApp caminando = mock(EstadoApp.class);
		SEM sistemaEstacionamiento = mock(SEM.class);
		EstadoGPS gpsEncendido = mock(Encendido.class);
		Estacionamiento estacionamiento = mock(Estacionamiento.class);
		Usuario usuario = mock(Usuario.class);
		
		
		AplicacionSEM app = new AplicacionSEM(modoManual,caminando,sistemaEstacionamiento,usuario);
		
		when(usuario.patenteDelAuto()).thenReturn("333ALO");
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
		
		assertEquals(100,app.consultarSaldo());
		
		//Verify
		
		verify(sistemaEstacionamiento).registrarEstacionamiento(estacionamiento);
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

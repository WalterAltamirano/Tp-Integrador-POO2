package testWalle;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

public class AplicacionSEMTest {
	
	private int nroDeCelular;
	private LocalDateTime horaDeInicio;
	private LocalDateTime horaDeFinalizacion;
	private Auto automovil;
	private Modo modoManual;
	private Modo modoAutomatico;
	private EstadoApp manejando;
	private SEM sistemaEstacionamiento;
	private EstadoGPS gps;
	private Estacionamiento estacionamiento;
	private Usuario usuario;
	private clasesWalle.AplicacionSEM app;
	
	@BeforeEach
	public void setUp() {
		nroDeCelular = 1123233232;
		horaDeInicio = mock(LocalDateTime.class);
		horaDeFinalizacion = mock(LocalDateTime.class);
		automovil = mock(Auto.class);
		modoManual = mock(ModoManual.class); 
		modoAutomatico= mock(ModoAutomatico.class); 
		manejando = mock(EstadoApp.class);
		sistemaEstacionamiento = mock(SEM.class);
		gps = mock(EstadoGPS.class);
		estacionamiento = mock(Estacionamiento.class);
		usuario = mock(Usuario.class);
		app = new clasesWalle.AplicacionSEM(modoManual,manejando,sistemaEstacionamiento,usuario,nroDeCelular,horaDeInicio,horaDeFinalizacion);
	}
	
	
	@Test
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSActivadoYModoAutomaticoYLaApliacionSEMIniciaElEstacionamiento() {
		
		//SetUp
		
		
		//when(usuario.patenteDelAuto()).thenReturn("333ALO");
		when(gps.estaEncendido()).thenReturn(true);
		when(sistemaEstacionamiento.verificarEstacionamientoVigente("333ALO")).thenReturn(false);
		when(automovil.getPatente()).thenReturn("333ALO");
		
		//Excercise
		
		app.elegirEstadoGPS(gps);
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
	public void testUnUsuarioDecideIniciarUnEstacionamientoConGPSDesactivadoYModoAutomaticoYLaApliacionSEMNoIniciaElEstacionamiento() {
		//SetUp
		
		
		//when(usuario.patenteDelAuto()).thenReturn("333ALO");
		when(gps.estaEncendido()).thenReturn(false);
		when(sistemaEstacionamiento.verificarEstacionamientoVigente("333ALO")).thenReturn(false);
		when(automovil.getPatente()).thenReturn("333ALO");
		doThrow(new Exception("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
				+ "Saldo: " + "100" +"Esta una zona valida: "+ "false" + "El auto ya esta estacionado: " + "false")).when(modoAutomatico).puedeEstacionar(app,"333ALO");
		//Excercise
				
		app.elegirEstadoGPS(gps);
		app.elegirModo(modoAutomatico);
		app.cargarSaldo(100);
		app.driving();
		app.walking(); //Se activaria solo el incio de estacionamiento
		//app.inicioEstacionamiento(1123233232, "333ALO");
				
		assertThrows(Exception.class, () ->  {app.inicioEstacionamiento(nroDeCelular, "333ALO");}); //No se descuenta el saldo
				
		//Verify
		verify(sistemaEstacionamiento).registrarEstacionamiento(estacionamiento);
		verify(manejando).manejando(app);
		verify(modoAutomatico).inicioDeEstacionamiento(app,nroDeCelular,"333ALO");
				
				
		//Tear Down
			
	}
	@Test
	public void testUnSensorIndicaQueElUsuarioPasoDeManejarACaminarEnUnaZonaEstacionamientoYLaAplicacionSEMAlertaAlUsuario() {
		
		
	}
	@Test
	public void test4() {
		
	}
}

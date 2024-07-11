package estadosAplicacion;

import org.junit.jupiter.api.*;

import aplicacionSEM.AplicacionSEM;
import dataUsuario.Auto;
import dataUsuario.Usuario;

import estrategiasAplicacion.Modo;
import estrategiasAplicacion.ModoAutomatico;
import estrategiasAplicacion.ModoManual;

import static org.mockito.Mockito.*;

public class EstadoTestCase {

	private EnAuto estadoEnAuto;
	private Caminando estadoCaminando;
	private AplicacionSEM aplicacion;
	private ModoAutomatico modoAutomatico;
	private ModoManual modoManual;
	private Usuario usuario;
	private Auto auto;
	private Modo modo;
	
	@BeforeEach
	public void setUp() {
		estadoEnAuto = new EnAuto();
		estadoCaminando = new Caminando();
		aplicacion = mock(AplicacionSEM.class);
		modoAutomatico = mock(ModoAutomatico.class);
		modoManual = mock(ModoManual.class);
		usuario = mock(Usuario.class);
		modo = mock(Modo.class);
		auto = mock(Auto.class);
	}
	
	@Test
	public void pruebaPasajeDeManejandoACaminando() {
		when(aplicacion.getModo()).thenReturn(modoAutomatico);
		estadoEnAuto.caminando(aplicacion);
		verify(aplicacion,atLeast(1)).pasoACaminando();
		//verify(aplicacion,atLeast(1)).inicioEstacionamiento(1234, "abc");
	}
	
	@Test
	public void pruebaPasajeDeManejandoAManejando() {
		estadoEnAuto.manejando(aplicacion);
	}
	
	@Test
	public void pruebaPasajeDeCaminandoAManejando() {
		when(aplicacion.getModo()).thenReturn(modoAutomatico);
		estadoCaminando.manejando(aplicacion);
		verify(aplicacion,atLeast(1)).pasoAAuto();
	}
	
	@Test
	public void pruebaPasajeDeCaminandoACaminando() {
		estadoCaminando.caminando(aplicacion);
	}
	
}

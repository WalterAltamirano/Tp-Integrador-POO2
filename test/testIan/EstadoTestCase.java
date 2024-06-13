package testIan;

import org.junit.jupiter.api.*;

import clasesIan.Auto;
import clasesIan.Caminando;
import clasesIan.EnAuto;
import clasesIan.EstadoApp;
import clasesIan.Usuario;
import clasesWalle.AplicacionSEM;
import clasesWalle.Modo;
import clasesWalle.ModoAutomatico;
import clasesWalle.ModoManual;

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
		/*when(aplicacion.getUsuario()).thenReturn(usuario);
		when(usuario.getPatente()).thenReturn("abc");
		when(aplicacion.getNumeroDeCelular()).thenReturn(1234);
		when(modo.estaEnModoAutomatico()).thenReturn(true);*/
		
		estadoEnAuto.caminando(aplicacion);
		verify(aplicacion,atLeast(1)).alertaInicioDeEstacionamiento();
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
		verify(aplicacion,atLeast(1)).alertaFinDeEstacionamiento();
	}
	
	@Test
	public void pruebaPasajeDeCaminandoACaminando() {
		estadoCaminando.caminando(aplicacion);
	}
}

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
		auto = new Auto("123");
	}
	
	@Test
	public void pruebaPasajeDeManejandoACaminando() {
		when(aplicacion.getModo()).thenReturn(modoAutomatico);
		/*when(aplicacion.getUsuario()).thenReturn(usuario);
		when(usuario.getPatente()).thenReturn(auto.getPatente());
		when(aplicacion.getNumeroDeCelular()).thenReturn(12345);
		when(modo.estaEnModoAutomatico()).thenReturn(true);*/
		
		estadoEnAuto.caminando(aplicacion);
		verify(aplicacion,atLeast(1)).alertaInicioDeEstacionamiento();
		//verify(aplicacion,atLeast(1)).inicioEstacionamiento(aplicacion.getNumeroDeCelular(), usuario.getPatente());
	}
	
	@Test
	public void pruebaPasajeDeCaminandoAManejando() {
		when(aplicacion.getModo()).thenReturn(modoAutomatico);
		estadoCaminando.manejando(aplicacion);
		verify(aplicacion,atLeast(1)).alertaFinDeEstacionamiento();
	}	
}

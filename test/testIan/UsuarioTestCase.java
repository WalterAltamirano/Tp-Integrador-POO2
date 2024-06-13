package testIan;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import clasesIan.Usuario;
import clasesMatias.PuntoDeVenta;

public class UsuarioTestCase {

	private Usuario usuario;
	private PuntoDeVenta puntoDeVenta;
	
	@BeforeEach
	public void setUp() {
		usuario = mock(Usuario.class);
		puntoDeVenta = mock(PuntoDeVenta.class);
	}
	
}

package testIan;

import org.junit.jupiter.api.*;

import clasesIan.EstadoApp;

import static org.mockito.Mockito.*;

public class EstadoTestCase {

	private EstadoApp estado;
	@BeforeEach
	public void setUp() {
		estado = mock(EstadoApp.class);
	}
	
}

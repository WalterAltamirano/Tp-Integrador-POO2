package dataUsuario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;



public class AutoTestCase {

	private Auto auto;
	
	@BeforeEach
	public void setUp() {
		auto = new Auto("1234");
	}
	
	@Test
	public void creacionDeAuto() {
		Auto auto2 = new Auto("5678");
	}
	
	@Test
	public void pruebaGetPatente() {
		assertEquals(auto.getPatente(), "1234");
	}
}

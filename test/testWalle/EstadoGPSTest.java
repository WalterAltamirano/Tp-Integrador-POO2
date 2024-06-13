package testWalle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
public class EstadoGPSTest {
	
	private EstadoGPS gpsEncendido;
	@BeforeEach
	public void setUp() {
		 gpsEncendido = new Encendido();
	}
	
	@Test
	public void testGpsEstaEncendido() {
		//SetUp
		
		//Excercise
		
		//Verify
		assertTrue(gpsEncendido.estaEncendido());
	}
	
}

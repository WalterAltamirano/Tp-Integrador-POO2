package testWalle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EstadoGPSApagadoTest {
	
	@BeforeEach
	public void setUp() {
		
	}
	
	
	@Test
	public void testGpsEstaApagado() {
		//SetUp
		ModoGps gps = new Apagado();
		
		//Excercise
		
		//Verify
		assertFalse(gps.getEstaEncendido());
	}
}

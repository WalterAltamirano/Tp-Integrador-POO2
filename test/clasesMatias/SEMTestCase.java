package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


public class SEMTestCase {
	
	private SEM sem;
	private ZonaDeEstacionamiento zona1;
	
	
	@BeforeEach 
	public void setUp () {
		//setUp
		zona1 = mock(ZonaDeEstacionamiento.class);
		
		sem = new SEM();
		
		
	}
	

	@Test
	public void añadirZonaDeEstacionamientoTest() {
		//Exercise
		sem.añadirZonaDeEstacionamiento(zona1);
		//Verify
		assertEquals(sem.getZonasDeEstacionamiento().size(), 1);
	}
	
	@Test
	public void getZonasDeEstacionamientoTest() {
		//Exercise
		sem.añadirZonaDeEstacionamiento(zona1);
		//Verify
		assertTrue(sem.getZonasDeEstacionamiento().contains(zona1));
	}


}

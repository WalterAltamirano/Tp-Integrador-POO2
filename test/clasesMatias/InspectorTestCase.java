package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import clasesIan.Auto;
import clasesIan.Estacionamiento;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class InspectorTestCase {
	
	
  private Inspector inspector;
  private SEM sem;
  private Auto auto1;
  private ZonaDeEstacionamiento zona1;
  
	@BeforeEach 
	public void setUp () {
		//setUp
		sem = mock(SEM.class);
		auto1 = mock(Auto.class);
		zona1 = mock(ZonaDeEstacionamiento.class);
		
		inspector = new Inspector(sem, zona1);
		
		
	}

	@Test
	public void inspeccionarAutoTest() {
		//exercise
		inspector.inspeccionarAuto(auto1);
		//verify
		verify(sem, atLeast(1)).verificarEstacionamientoVigente(auto1.getPatente());
	}
	
	@Test
	public void AltaDeInfraccionTest() {
		//exercise
		inspector.altaDeInfraccion(auto1);
		//verify
		assertEquals(sem.getInfraccionesRegistradas().size(), 1);
	}
	
	

}

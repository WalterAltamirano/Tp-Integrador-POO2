package zonaEstacionamientoEInspector;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import dataUsuario.Auto;

import sistemaEstacionamientoMunicipal.SEM;


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
		verify(sem, atLeast(1)).verificarEstacionamientoVigentePorPatente(auto1.getPatente());
	}
	
	@Test
	public void AltaDeInfraccionTest() {
		//exercise
		Infraccion infraccion = inspector.altaDeInfraccion(auto1.getPatente());
		//verify
		assertEquals(infraccion.getPatenteAutoEnInfraccion(), auto1.getPatente());
	}
	
	@Test
	public void cargarInfraccionTest() {
	//exercise
	Infraccion infraccion = inspector.altaDeInfraccion(auto1.getPatente());	
	inspector.cargarInfraccion(infraccion);
	//verify
	verify(sem, atLeast(1)).registrarInfraccion(infraccion);
	
	}
	
	

}

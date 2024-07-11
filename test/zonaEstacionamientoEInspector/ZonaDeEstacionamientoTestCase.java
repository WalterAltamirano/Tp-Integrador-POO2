package zonaEstacionamientoEInspector;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import puntoDeVentaYCompras.PuntoDeVenta;


import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

public class ZonaDeEstacionamientoTestCase {
	
	private ZonaDeEstacionamiento zona1;
	private Inspector inspector1;
	private PuntoDeVenta puntoVenta1;
	
	@BeforeEach 
	public void setUp () {
		//setUp
		
		
		inspector1 = mock(Inspector.class);
		puntoVenta1 = mock(PuntoDeVenta.class);
		
		zona1 = new ZonaDeEstacionamiento(inspector1);
				
	}

	@Test
	public void añadirPuntoDeVentaTest() {
		//Exercise
		zona1.añadirPuntoDeVenta(puntoVenta1);
		//Verify
		assertEquals(zona1.getPuntosDeVenta().size(), 1);
	}
	
	@Test
	public void getPuntosDeVenta() {
		//Exercise
		zona1.añadirPuntoDeVenta(puntoVenta1);
		//Verify
		assertTrue(zona1.getPuntosDeVenta().contains(puntoVenta1));
	}
	
	@Test
	public void getInspectorAsignado() {
		
		//Verify
		assertEquals(zona1.getInspectorAsignado() , inspector1);
	}

}

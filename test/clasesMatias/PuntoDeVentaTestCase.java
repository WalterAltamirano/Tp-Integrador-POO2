package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clasesIan.Auto;
import clasesWalle.AplicacionSEM;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;

class PuntoDeVentaTestCase {
	
	private PuntoDeVenta puntoVenta1;
	private AplicacionSEM aplicacion;
	private SEM sem;
	private Auto auto;
	
	
	@BeforeEach 
	public void setUp () {
		//setUp
		
		
		sem = mock(SEM.class);
		aplicacion = mock(AplicacionSEM.class);
		auto = mock(Auto.class);
		
		
		puntoVenta1 = new PuntoDeVenta();
		
		puntoVenta1.setSem(sem);
				
	}

	@Test
	public void cargarSaldoTest() {
				
		//exercise
		puntoVenta1.cargarSaldo(aplicacion, 50);
		//verify		
		verify(aplicacion, atLeast(1)).cargarSaldo(50);	
		assertEquals(sem.getCompras().size(), 1);
	}
	
	@Test
	public void registrarEstacionamientoTest() {
				
		//exercise
		puntoVenta1.registrarEstacionamiento(auto.getPatente(), 4);
		//verify		
		assertEquals(sem.getEstacionamientos().size(), 1);
		assertEquals(sem.getCompras().size(), 1);
		
	}


}

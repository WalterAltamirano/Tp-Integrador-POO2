package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import clasesIan.Auto;
import clasesIan.EstacionamientoCompraPuntual;
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
		
		
	}
	
	@Test
	public void generarCompraSaldoTest() {
		//exercise
		CargaDeSaldo nuevaCarga = puntoVenta1.generarCompraSaldo(10, aplicacion);
		//verify
		assertEquals(nuevaCarga.getMontoCargado(), 10);
	}
	
	@Test
	public void registrarEstacionamientoTest() {
		//exercise
		puntoVenta1.registrarEstacionamiento("123456", 4);
		//verify
		verify(sem, atLeast(1)).notificarOrganismosInteresados();
		
		
	}
	
	@Test
	public void generarNuevaCompraYNuevoEstacionamientoTest() {
				
		//exercise
		CompraPuntual nuevaCompra = puntoVenta1.generarNuevaCompra(4);
		EstacionamientoCompraPuntual nuevoEstacionamiento = puntoVenta1.generarNuevoEstacionamiento(nuevaCompra, auto.getPatente());
		//verify		
		verify(sem, atLeast(1)).registrarCompra(nuevaCompra);
		verify(sem, atLeast(1)).registrarEstacionamiento(nuevoEstacionamiento);
		
	}


}

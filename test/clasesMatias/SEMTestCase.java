package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import clasesIan.Estacionamiento;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;


public class SEMTestCase {
	
	private SEM sem;
	private ZonaDeEstacionamiento zona1;
	private Estacionamiento estacionamiento1;
	private Compra cargaDeSaldo;
	private Compra compraPuntual;
	
	
	@BeforeEach 
	public void setUp () {
		//setUp
		zona1 = mock(ZonaDeEstacionamiento.class);
		estacionamiento1 = mock(Estacionamiento.class);
		cargaDeSaldo = mock(CargaDeSaldo.class);
		compraPuntual = mock(CompraPuntual.class);
		
		sem = new SEM();
		
		
	}
	

	@Test
	public void registrarCompraTest() {
		//Exercise
		sem.registrarCompra(compraPuntual);
		sem.registrarCompra(cargaDeSaldo);
		//Verify
		assertEquals(sem.getCompras().size(), 2);
	}
	
	@Test
	public void getCompras() {
		//Exercise
		sem.registrarCompra(compraPuntual);
		sem.registrarCompra(cargaDeSaldo);
		//Verify
		assertTrue(sem.getCompras().contains(compraPuntual));
		assertTrue(sem.getCompras().contains(cargaDeSaldo));
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
	
	@Test
	public void registrarEstacionamientoTest() {
		//Exercise
		sem.registrarEstacionamiento(estacionamiento1);
		//Verify
		assertEquals(sem.getEstacionamientos().size(), 1);
	}
	
	@Test
	public void getEstacionamientosTest() {
		//Exercise
		sem.registrarEstacionamiento(estacionamiento1);
		//Verify
		assertTrue(sem.getEstacionamientos().contains(estacionamiento1));
	}
	
	@Test
	public void finDeFranjaHorariaTest() {
		//Exercise
		sem.registrarEstacionamiento(estacionamiento1);
		sem.finDeFranjaHoraria();
		//Verify
		verify(estacionamiento1, atLeast(1)).finalizarEstacionamiento();
	}


}

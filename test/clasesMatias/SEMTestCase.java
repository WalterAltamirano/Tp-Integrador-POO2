package clasesMatias;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import clasesIan.Estacionamiento;
import clasesWalle.AplicacionSEM;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.*;


public class SEMTestCase {
	
	private SEM sem;
	private ZonaDeEstacionamiento zona1;
	private Estacionamiento estacionamiento1;
	private Compra cargaDeSaldo;
	private Compra compraPuntual;
	private Infraccion infraccion1;
	private INotificar organismoInteresado;
	private AplicacionSEM aplicacion1;
	
	@BeforeEach 
	public void setUp () {
		//setUp
		zona1 = mock(ZonaDeEstacionamiento.class);
		estacionamiento1 = mock(Estacionamiento.class);
		cargaDeSaldo = mock(CargaDeSaldo.class);
		compraPuntual = mock(CompraPuntual.class);
		infraccion1 = mock(Infraccion.class);
		organismoInteresado = mock(INotificar.class);
		aplicacion1 = mock(AplicacionSEM.class);
		
		sem = new SEM();
		
		
	}
	
	@Test
	public void registrarAplicacionTest() {
		//Exercise
		sem.registrarAplicacion(aplicacion1);
		
		//Verify
		assertEquals(sem.getAplicacionesRegistradas().size(), 1);
	}
	
	@Test
	public void getAplicacionesRegistradasTest() {
		//Exercise
		sem.registrarAplicacion(aplicacion1);
		//Verify
		assertTrue(sem.getAplicacionesRegistradas().contains(aplicacion1));
		
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
		sem.registrarAplicacion(aplicacion1);
		
		sem.finDeFranjaHoraria();
		//Verify
		verify(aplicacion1, atLeast(1)).finalizarEstacionamiento(aplicacion1.getNumeroDeCelular());
	}
	

	
	@Test
	public void registrarInfraccionTest() {
		//Exercise
		sem.registrarInfraccion(infraccion1);
		//Verify
		assertEquals(sem.getInfraccionesRegistradas().size(), 1);
	}
	
	@Test
	public void getInfraccionesRegistradasTest() {
		//Exercise
		sem.registrarInfraccion(infraccion1);
		//Verify
		assertTrue(sem.getInfraccionesRegistradas().contains(infraccion1));
	}
	
	@Test 
	public void buscarPorNumeroDeCelularTest() {
		when(estacionamiento1.getNumeroDeCelularDeEstacionamiento()).thenReturn(123456);
		//exercise
		sem.registrarEstacionamiento(estacionamiento1);
		
		//verify
		assertEquals(sem.buscarPorNumeroCelular(123456), estacionamiento1);
	}
	
	@Test
	public void suscribirOrganismosInteresadosTest() {
		//Exercise
		sem.suscribirOrganismosInteresados(organismoInteresado);
		//Verify
		assertEquals(sem.getOrganismosInteresados().size(), 1);
	}
	
	@Test
	public void desuscribirOrganismosInteresadosTest() {
		//Exercise
		sem.suscribirOrganismosInteresados(organismoInteresado);
		sem.desuscribirOrganismosInteresados(organismoInteresado);
		//Verify
		assertFalse(sem.getOrganismosInteresados().contains(organismoInteresado));
	}
	
	@Test
	public void getOrganismosInteresadosTest() {
		//Exercise
		sem.suscribirOrganismosInteresados(organismoInteresado);
		//Verify
		assertTrue(sem.getOrganismosInteresados().contains(organismoInteresado));
	}
	
	@Test
	public void notificarOrganismosInteresadosTest() {
		//Exercise
		sem.suscribirOrganismosInteresados(organismoInteresado);
		sem.notificarOrganismosInteresados();
		//verify
		verify(organismoInteresado, atLeast(1)).actualizar(sem);
	}
	
	@Test
	public void verificarEstacionamientoVigenteTest() {
		when(estacionamiento1.estaVigente()).thenReturn(true);
		when(estacionamiento1.getPatente()).thenReturn("123456");
		//Exercise
		sem.registrarEstacionamiento(estacionamiento1);
		//Verify
		assertTrue(sem.verificarEstacionamientoVigente(estacionamiento1.getNumeroDeCelularDeEstacionamiento()));
		assertTrue(sem.verificarEstacionamientoVigente(estacionamiento1.getPatente()));
		
	}
	
	@Test
	public void finalizarEstacionamiento() {
		
		//Exercise
		sem.registrarEstacionamiento(estacionamiento1);
		sem.finalizarEstacionamientoCon(estacionamiento1.getNumeroDeCelularDeEstacionamiento());
		//verify
		assertFalse(estacionamiento1.estaVigente());
	}
	


}

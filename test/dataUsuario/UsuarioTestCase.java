package dataUsuario;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.*;

import aplicacionSEM.AplicacionSEM;

import puntoDeVentaYCompras.PuntoDeVenta;

public class UsuarioTestCase {

	private AplicacionSEM aplicacion;
	private PuntoDeVenta puntoDeVenta;
	private Auto auto;
	private Usuario usuario;
	
	@BeforeEach
	public void setUp() {
		aplicacion = mock(AplicacionSEM.class);
		puntoDeVenta = mock(PuntoDeVenta.class);
		auto = mock(Auto.class);
		usuario = new Usuario(auto, aplicacion);
	}
	
	@Test
	public void pruebaGetPatente() {
		usuario.getPatente();
		verify(auto,atLeast(1)).getPatente();
	}
	
	@Test
	public void pruebaGetAplicacion() {
		assertEquals(usuario.getAplicacion(), aplicacion);
	}
	
	@Test
	public void pruebaEstacionamientoCompraPuntual() {
		usuario.estacionamientoCompraPuntual("12345", 5, puntoDeVenta);
		verify(puntoDeVenta,atLeast(1)).registrarEstacionamiento("12345", 5);
	}
	
	@Test
	public void cargaDeSaldoUsuario(){
		usuario.solicitarCargaDeSaldo(puntoDeVenta, 5);
		verify(puntoDeVenta,atLeast(1)).cargarSaldo(usuario.getAplicacion(), 5);
	}
	
	@Test
	public void pruebaEstacionamientoPorAplicacion() {
		usuario.estacionamientoPorAplicacion(4);
		verify(aplicacion,atLeast(1)).iniciarEstacionamiento(4);
	}
}

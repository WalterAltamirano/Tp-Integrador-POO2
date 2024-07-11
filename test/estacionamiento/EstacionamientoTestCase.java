package testIan;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import clasesIan.EstacionamientoAplicacion;
import clasesIan.EstacionamientoCompraPuntual;
import clasesMatias.Compra;
import clasesMatias.CompraPuntual;
import clasesWalle.AplicacionSEM;

public class EstacionamientoTestCase {

	
	private EstacionamientoAplicacion estacionamientoAplicacion;
	private EstacionamientoCompraPuntual estacionamientoCompraPuntual;
	private CompraPuntual compraPuntual;
	
	@BeforeEach
	public void setUp() {
		//usuario = mock(Usuario.class);
		estacionamientoAplicacion = new EstacionamientoAplicacion(1234, "abc");
		compraPuntual = mock(CompraPuntual.class);
		estacionamientoCompraPuntual = new EstacionamientoCompraPuntual(compraPuntual, "xyz");
	}
	
	//EstacionamientoAplicacion
	@Test
	public void setHoraDeInicio() {
		estacionamientoAplicacion.setHoraDeFin(LocalDateTime.now());
	}
	
	@Test
	public void noEstaVigente() {
		estacionamientoAplicacion.setHoraDeFin(LocalDateTime.now());
		assertEquals(estacionamientoAplicacion.estaVigente(), false);
	}
	
	@Test
	public void estaVigente() {
		estacionamientoAplicacion.setHoraDeFin(LocalDateTime.now().plusHours(2));
		assertEquals(estacionamientoAplicacion.estaVigente(), true);
	}
	
	@Test
	public void pruebaGetNumeroDeCelular() {
		assertEquals(estacionamientoAplicacion.getNumeroDeCelularDeEstacionamiento(), 1234);
	}

	@Test
	public void notificarFinalizacion() {
		estacionamientoAplicacion.setHoraDeFin(LocalDateTime.now().minusHours(2));
		estacionamientoAplicacion.notificarFinalizacion();
	}
	
	@Test
	public void estacionamientoFinalizado() {
		estacionamientoAplicacion.finalizarEstacionamiento();
		assertEquals(estacionamientoAplicacion.getViencia(), false);
	}
	
	@Test
	public void getPatente() {
		assertEquals(estacionamientoAplicacion.getPatente(), "abc");
	}
	
	@Test
	public void getNumeroDeCelularDeEstacionamientoAplicacion() {
		assertEquals(estacionamientoAplicacion.getNumeroDeCelularDeEstacionamiento(), 1234);
	}
	
	
	
	//EstacionamientoCompraPuntual
	@Test
	public void noEstaVigenteEstacionamientoCompraPuntual() {
		assertEquals(estacionamientoCompraPuntual.estaVigente(), false);
	}
	
	@Test
	public void pruebaGetNumeroDeCelularEstacionamientoCompraPuntual() {
		assertEquals(estacionamientoCompraPuntual.getNumeroDeCelularDeEstacionamiento(), 0);
	}

	@Test
	public void notificarFinalizacionEstacionamientoCompraPuntual() {
		estacionamientoCompraPuntual.notificarFinalizacion();
	}
	
	@Test
	public void estacionamientoFinalizadoEstacionamientoCompraPuntual() {
		estacionamientoCompraPuntual.finalizarEstacionamiento();
		assertEquals(estacionamientoAplicacion.getViencia(), false);
	}
	
	@Test
	public void getPatenteEstacionamientoCompraPuntual() {
		assertEquals(estacionamientoCompraPuntual.getPatente(), "xyz");
	}
	
}

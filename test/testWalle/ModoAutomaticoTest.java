package testWalle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

public class ModoAutomaticoTest {
	
	private Modo modo;
	private AplicacionSEM app;
	private int nroDeCelular;
	private String patente;
	private Estacionamiento estacionamiento;
	private SEM sistema;
	private EstrategiaGPS gps;
	
	@BeforeEach
	public void setUp() {

		 app = mock(AplicacionSEM.class);
		 nroDeCelular = 1123234444;
		 patente = "444JEO";
		 sistema = mock(SEM.class);
		 estacionamiento = mock(EstacionamientoAplicacion.class);
		 gps = mock(EstrategiaGPS.class);
		 modo= new ModoAutomatico();
	}
	
	@Test
	public void testAplicacionRecibeUnInicioDeEstacionamientoYDelegaEnModoUnInicioDeEstacionamientoPeroNoSeInicia() {
		
		//SetUp
		when(app.getNumeroDeCelular()).thenReturn(nroDeCelular);
		//Excercise
		modo.inicioDeEstacionamiento(app);
		
		//Verify
		verify(app).iniciarEstacionamiento(nroDeCelular,""); 
		
	}
	@Test
	public void testModoAutomaticoRecibeUnInicioDeEstacionamientoEnYSeInicia() {
		
		//SetUp
		when(app.getNumeroDeCelular()).thenReturn(nroDeCelular);
		//Excercise
		modo.finDeEstacionamiento(app);
		//Verify
		verify(app).finalizarEstacionamiento(nroDeCelular);
		verify(app).getNumeroDeCelular();
	}
}

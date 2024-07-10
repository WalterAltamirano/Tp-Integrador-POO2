package testWalle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;

public class ModoManualTest {
	
	private Modo modo;
	private AplicacionSEM app;
	private int nroDeCelular;
	private String patente;
	private Estacionamiento estacionamiento;
	private SEM sistema;
	//private ModoGps gps;
	
	@BeforeEach
	public void setUp() {

		 app = mock(AplicacionSEM.class);
		 nroDeCelular = 1123234444;
		 patente = "444JEO";
		 sistema = mock(SEM.class);
		 estacionamiento = mock(Estacionamiento.class);
		// gps = mock(ModoGps.class);
		 modo= new ModoManual();
	}
	@Test
	public void testUnModoManualRecibeUnFinDeEstacionamientoYLaAppNoFinalizaNingunEstacionamiento() {
		
		//Excercise
		modo.finDeEstacionamiento(app);
		//Verify
		verify(app,never()).finalizarEstacionamiento();
	}
	@Test
	public void testUnModoManualRecibeUnInicioDeEstacionamientoYLaAppNoIniciaElEstacionamiento() {
		
		//SetUp
		//Excercise
		modo.inicioDeEstacionamiento(app);
		//Verify
		verify(app,never()).iniciarEstacionamiento();
	}
	
}

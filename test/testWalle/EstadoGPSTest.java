package testWalle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import clasesIan.*;
import clasesMatias.*;
import clasesWalle.*;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
public class EstadoGPSTest {
	
	private ModoGps gps;
	private ModoGps gpsEncendido;
	private AplicacionSEM app;
	@BeforeEach
	public void setUp() {
		 gps = new Apagado();
		 app = mock(AplicacionSEM.class);
		 gpsEncendido = mock(Encendido.class);
	}
	
//	@Test
//	public void testGpsEstaEncendido() {
//		//SetUp
//		
//		//Excercise
//		gps.prender(app);
//		//Verify
//		assertTrue(gps.getEstaEncendido());
//		
//		//verify(app,atLeast(1)).setEstadoGPS(gpsEncendido); 
//		//no es posible porque es diferente el hashCode del mock "gpsEncendido"
//		//Que el del cual se crea internamente en gps al enviarle el mensaje "prender()" 
//	}
//	
}

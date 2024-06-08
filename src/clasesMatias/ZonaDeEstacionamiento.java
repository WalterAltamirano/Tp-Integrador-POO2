package clasesMatias;

import java.util.ArrayList;
import java.util.List;

import org.mockito.ArgumentMatchers;

public class ZonaDeEstacionamiento {

	private Inspector inspectorAsignado;
	private List<PuntoDeVenta> puntosDeVenta = new ArrayList<PuntoDeVenta>();;

	public ZonaDeEstacionamiento(Inspector inspector) {
		this.inspectorAsignado = inspector;
	}

	public void añadirPuntoDeVenta(PuntoDeVenta puntoVenta) {
		this.getPuntosDeVenta().add(puntoVenta);
		
	}

	public List<PuntoDeVenta> getPuntosDeVenta() {
		
		return this.puntosDeVenta;
	}

	public Inspector getInspectorAsignado() {
		
		return this.inspectorAsignado;
	}

}

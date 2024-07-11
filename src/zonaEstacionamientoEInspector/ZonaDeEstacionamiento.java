package zonaEstacionamientoEInspector;

import java.util.ArrayList;
import java.util.List;

import puntoDeVentaYCompras.PuntoDeVenta;

/** Clase que representa una ZonaDeEstacionamiento con un Ispector asignado y su lista de PuntosDeVenta
 * 
 *  Autor: MazaVega Matias
 * */

public class ZonaDeEstacionamiento {

	private Inspector inspectorAsignado;
	private List<PuntoDeVenta> puntosDeVenta = new ArrayList<PuntoDeVenta>();
    
	/*Se inicializa recibiendo un Inspector como Inspector asignado
	 * */
	public ZonaDeEstacionamiento(Inspector inspector) {
		this.inspectorAsignado = inspector;
	}

	//agrega un PuntoDeVenta a la lsta de puntos de venta
	public void añadirPuntoDeVenta(PuntoDeVenta puntoVenta) {
		this.getPuntosDeVenta().add(puntoVenta);
		
	}
    
	// retorna la lista de Puntos de venta
	public List<PuntoDeVenta> getPuntosDeVenta() {
		
		return this.puntosDeVenta;
	}
	
	//retorna el Inspector asignado
	public Inspector getInspectorAsignado() {
		
		return this.inspectorAsignado;
	}

}

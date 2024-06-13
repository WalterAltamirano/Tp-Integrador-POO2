package clasesMatias;

import clasesIan.Auto;

/** Clase que representa al Inspector asignado a una ZonaDeEstacionamiento
 * 
 *  Autor: MazaVega Matias
 * */

public class Inspector {

	private ZonaDeEstacionamiento zonaAsignada;
	private SEM empleador;

	/*Se inicializa recibiendo un SEM que representa a su "empleador" 
	 * y la ZonaDeEstacionamiento a la que esta asignado
	 * */
	public Inspector(SEM sem, ZonaDeEstacionamiento zona) {
		this.zonaAsignada = zona;
		this.empleador = sem;
	}

	//consulta al SEM si hay algun Estacionamiento vigente para un Auto determinado
	//en caso de no haberlo genera una Infraccion y le pide al SEM que la añada a sus infracciones registradas
	public void inspeccionarAuto(Auto auto) {
		boolean vigencia = this.empleador.verificarEstacionamientoVigente(auto.getPatente());
		if (vigencia == false) {
			Infraccion infraccion = this.altaDeInfraccion(auto.getPatente());
			this.cargarInfraccion(infraccion);
		}
		
	}

	
    // genera una nueva instancia de Infraccion y la retorna
	public Infraccion altaDeInfraccion(String patente) {
		Infraccion infraccion = new Infraccion(patente, this);
		
		return infraccion;
	}
	
	//le pide al SEM que agregue una Infraccion a su lista de Infracciones registradas
	public void cargarInfraccion(Infraccion infraccion) {
		this.empleador.registrarInfraccion(infraccion);
		
	}
    // devuelve la ZonaDeEstacionamiento que tiene asignada el Inspector
	public ZonaDeEstacionamiento getZonaAsignada() {
		return zonaAsignada;
	}

}

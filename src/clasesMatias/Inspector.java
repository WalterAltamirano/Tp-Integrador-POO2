package clasesMatias;

import clasesIan.Auto;

public class Inspector {

	private ZonaDeEstacionamiento zonaAsignada;
	private SEM empleador;

	public Inspector(SEM sem, ZonaDeEstacionamiento zona) {
		this.zonaAsignada = zona;
		this.empleador = sem;
	}

	public void inspeccionarAuto(Auto auto) {
		boolean vigencia = this.empleador.verificarEstacionamientoVigente(auto.getPatente());
		if (vigencia == false) {
			Infraccion infraccion = this.altaDeInfraccion(auto.getPatente());
			this.cargarInfraccion(infraccion);
		}
		
	}

	

	public Infraccion altaDeInfraccion(String patente) {
		Infraccion infraccion = new Infraccion(patente, this);
		
		return infraccion;
	}
	
	public void cargarInfraccion(Infraccion infraccion) {
		this.empleador.registrarInfraccion(infraccion);
		
	}

	public ZonaDeEstacionamiento getZonaAsignada() {
		return zonaAsignada;
	}

}

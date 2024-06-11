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
			this.altaDeInfraccion(auto);
		}
		
	}

	public void altaDeInfraccion(Auto auto) {
		Infraccion infraccion = new Infraccion(auto.getPatente(), this);
		this.empleador.registrarInfraccion(infraccion);
	}

	public ZonaDeEstacionamiento getZonaAsignada() {
		return zonaAsignada;
	}

}

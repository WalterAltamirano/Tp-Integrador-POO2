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
		boolean vigencia = this.empleador.verificarEstacionamientoVigente(auto);
		if (vigencia == false) {
			this.AltaDeInfraccion(auto);
		}
		
	}

	private void AltaDeInfraccion(Auto auto) {
		Infraccion infraccion = new Infraccion(auto, this);
		
	}

}

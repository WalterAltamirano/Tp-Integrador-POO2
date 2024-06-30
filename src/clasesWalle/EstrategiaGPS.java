package clasesWalle;

import clasesMatias.ZonaDeEstacionamiento;

public abstract class EstrategiaGPS {

	//Cambios
	protected boolean estaEncendido;
	
	//Cambios
	public boolean getEstaEncendido() {
		return this.estaEncendido;
	}
	public void setEstaEncendido(boolean valor) {
		this.estaEncendido = valor;
	}
	
	
	public abstract boolean coincidenEnUnMismoPunto(AplicacionSEM app,ZonaDeEstacionamiento zona);
}

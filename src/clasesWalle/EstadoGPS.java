package clasesWalle;

import clasesMatias.ZonaDeEstacionamiento;

public abstract class EstadoGPS {

	//Cambios
	protected boolean estaEncendido;
	
	//Cambios
	public boolean getEstaEncendido() {
		return this.estaEncendido;
	}
	public void setEstaEncendido(boolean valor) {
		this.estaEncendido = valor;
	}
	
	public abstract void prender(AplicacionSEM app);
		
	public abstract void apagar(AplicacionSEM app);
	
	public abstract boolean coincidenEnUnMismoPunto(AplicacionSEM app,ZonaDeEstacionamiento zona);
}

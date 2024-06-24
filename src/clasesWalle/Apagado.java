package clasesWalle;

import clasesMatias.ZonaDeEstacionamiento;

public class Apagado extends EstadoGPS{

	@Override
	public boolean coincidenEnUnMismoPunto(AplicacionSEM app, ZonaDeEstacionamiento zona) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void prender(AplicacionSEM app) {
		this.setEstaEncendido(true);
		app.setEstadoGPS(new Encendido());
	}

	@Override
	public void apagar(AplicacionSEM app) {
		//Ya esta apagado
	}



}

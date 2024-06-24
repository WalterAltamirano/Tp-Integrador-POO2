package clasesWalle;

import clasesMatias.ZonaDeEstacionamiento;

public class Encendido extends EstadoGPS {

	@Override
	public boolean coincidenEnUnMismoPunto(AplicacionSEM app, ZonaDeEstacionamiento zona) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void prender(AplicacionSEM app) {
		//Ya esta prendido
		
	}

	@Override
	public void apagar(AplicacionSEM app) {
		this.setEstaEncendido(false);
		app.setEstadoGPS(new Apagado());
	}

	

}

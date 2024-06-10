package clasesWalle;

public class Caminando extends EstadoApp{

	@Override
	public void caminando(AplicacionSEM app) {
//		if(!hayEstacionamientoVigente() && estaEnZonaEstacionamiento()) {
		//Tiene que alertar al usuario que se va a iniciar un estacionamiento
		//app.modo.notificarAlertaInicio();
//	}
		//this.estaCaminando = true;
	}

	@Override
	public void manejando(AplicacionSEM app) {
		//if(hayEstacionamientoVigente() && coincideElPuntoGeografico) {
	         //Tiene que alertar que finaliza el estacionamiento
			 //app.modo.notificarAlertaFin();
    //    }
		app.setEstado(new EnAuto());
		
	}
	
}

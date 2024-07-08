package clasesWalle;


public class ModoAutomatico implements Modo{


	@Override
	public void avisoDeCambio() {
		System.out.print("Usted a elegido el Modo Automatico");
	}

	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {
		try {	
			this.puedeAlertarInicio(app);
			this.alertarInicioDeEstacionamiento();
		}
		catch(ExcepcionPersonalizada e) {
		}
		app.iniciarEstacionamiento();
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		try {
			this.puedeAlertarFin(app);
			this.alertarfinDeEstacionamiento();
		}
		catch(ExcepcionPersonalizada e) {
		}
		app.finalizarEstacionamiento();
	}
	
	//Se envia al usuario si se cumplen las condiciones
	public void alertarInicioDeEstacionamiento() {
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria iniciarse su estacionamiento");
	}
	//Se envia al usuario si se cumplen las condiciones
	public void alertarfinDeEstacionamiento() {
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria finalizarse su estacionamiento");
	}
	
	@Override
	public void puedeAlertarInicio(AplicacionSEM app) throws ExcepcionPersonalizada {
		this.laAppEstaEnZonaValida(app);
		if(app.hayEstacionamientoCon(app.getPatente())) {
			throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
		}
	}
	@Override
	public void puedeAlertarFin(AplicacionSEM app) throws ExcepcionPersonalizada {
		this.laAppEstaEnZonaValida(app);
		if(!app.hayEstacionamientoCon(app.getNumeroDeCelular())) {
			throw new ExcepcionPersonalizada("No se puede finalizar. No hay estacionamiento vigente.");
		}
	}
	
	public void laAppEstaEnZonaValida(AplicacionSEM app) throws ExcepcionPersonalizada{
		if(!app.estaEnZonaDeEstacionamiento()) {
			throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
		}
	}
}

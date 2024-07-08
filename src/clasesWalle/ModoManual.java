package clasesWalle;


public class ModoManual implements Modo{

	@Override
	public void avisoDeCambio() {//Se muestra en la app, no se envia al usuario
		System.out.print( "Usted a elegido el Modo Manual");
	}
	
	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {
		
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		
	}

	@Override
	public void puedeAlertarInicio(AplicacionSEM app) throws ExcepcionPersonalizada {
		
	}

	@Override
	public void puedeAlertarFin(AplicacionSEM app) throws ExcepcionPersonalizada {
		
	}
}

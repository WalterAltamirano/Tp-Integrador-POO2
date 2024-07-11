package clasesWalle;


public class ModoManual implements Modo{

	@Override
	public void avisoDeCambio() {//Se muestra en la app, no se envia al usuario
		System.out.print( "Usted a elegido el Modo Manual");
	}
	
	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: Usted esta en modo manual. Por lo que deberia iniciar su estacionamiento");	
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: Usted esta en modo manual. Por lo que deberia finalizar su estacionamiento");
	}

	
}

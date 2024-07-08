package clasesWalle;


public class ModoAutomatico implements Modo{


	@Override
	public void avisoDeCambio() {
		System.out.print("Usted a elegido el Modo Automatico");
	}

	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria iniciarse su estacionamiento");
		app.iniciarEstacionamiento();
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria finalizarse su estacionamiento");
		app.finalizarEstacionamiento();
	}
		

}

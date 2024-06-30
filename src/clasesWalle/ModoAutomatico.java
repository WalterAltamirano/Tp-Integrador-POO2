package clasesWalle;


public class ModoAutomatico extends Modo{


	
	
	
//	@Override
//	public void puedeEstacionar(AplicacionSEM app, String patente) throws ExcepcionPersonalizada{
//		super.puedeEstacionar(app, patente); 
//		if(!app.elGpsEstaEncendido()) {
//			throw new ExcepcionPersonalizada("El gps esta apagado. Para usar el modo automatico tenes que prenderlo");
//		}
//	}
//	

	@Override
	public void avisoDeCambio() {
		System.out.print("Usted a elegido el Modo Automatico");
	}

	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {//Faltan parametros
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria iniciarse su estacionamiento");
		app.iniciarEstacionamiento(app.getNumeroDeCelular(), "");
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: Usted esta en modo automatico, por lo que podria finalizarse su estacionamiento");
		app.finalizarEstacionamiento(app.getNumeroDeCelular());
	}
}

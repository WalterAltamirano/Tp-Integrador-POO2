package clasesWalle;


public class ModoManual extends Modo{


//	@Override
//	public void darRespuestaInicial(AplicacionSEM app) {	//Lo tendria que tener?
//		System.out.print("!---------------------------------!"
//				 + "                                        "
//				 + "Su estacionamiento fue dado de alta con exito."
//				 + "Hora exacta: " + app.getHoraInicio()
//				 + "Hora fin: " + "Pendiente"
//				 + "Esta en Modo Manual, usted debe encargarse de finalizar el estacionamiento."
//				 + "De lo contrario, la hora de fin quedara establecida hasta que:"
//				 + "Sean las 20:00."
//				 + "Usted mismo finalize el estacionamiento antes de las 20:00,"
//				 + "descontando de su saldo el credito disponible segun la cantidad de horas que estuvo"
//				 + "o de lo contrario descontando saldo de mas si no alcanza a cubrir el equivalente de"
//				 + "saldo acreditado con la cantidad de horas que dejo su coche"
//				 + "Notese que le quedara saldo negativo para esta ultima opcion"
//				 + "!---------------------------------!");
//	}

	@Override
	public void avisoDeCambio() {
		System.out.print("!---------------------------------!"
				+ "Usted a elegido el Modo Manual");
	}

//	@Override								
//	public void darRespuestaFinal(AplicacionSEM app) {       //Lo tendria que tener?
//		System.out.print("!---------------------------------!"
//				 + "Su estacionamiento fue dado de baja con exito."
//				 + "Hora exacta: " + app.getHoraInicio() + ":" + app.minutoInicio()
//				 + "Hora fin: " + app.calcularHoraFin() + ":" + app.minutoFin()
//				 + "Duracion " + (app.horaFinal() - app.getHoraInicio())
//				 + "Costo: " + app.calcularCreditoAPagar()
//				 + "!---------------------------------!");
//	}

	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: No se ha iniciado el estacionamiento por que usted elijio el modo Manual"
				+ "Procure iniciar el estacionamiento para evitar una infraccion");
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app) {
		System.out.print("Alerta: No se ha finalizado el estacionamiento por que usted elijio el modo Manual"
				+ "Procure finalizar el estacionamiento para evitar descontar saldo de mas");
	}


	
	
}

package clasesWalle;

import clasesIan.EstacionamientoAplicacion;

public class ModoManual extends Modo{
	
	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente)  {
		try {
			this.puedeEstacionar(app,patente);
			app.getSistemaEstacionamiento()
			.registrarEstacionamiento(new EstacionamientoAplicacion(numeroDeCelular,patente));
			this.darRespuesta(app);
		}
		catch (Exception e) {
		}
	}

	
	@Override
	public void finDeEstacionamiento(AplicacionSEM app,int numeroDeCelular) {
		
	}

	@Override
	public void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			app.getUsuario().notificarAlerta("Alerta: No se ha iniciado el estacionamiento por que usted elijio el modo Manual"
					+ "Procure iniciar el estacionamiento para evitar una infraccion");
			System.out.print("Alerta: No se ha iniciado el estacionamiento por que usted elijio el modo Manual"
					+ "Procure iniciar el estacionamiento para evitar una infraccion");
		}
	}

	@Override
	public void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			app.getUsuario().notificarAlerta("Alerta: No se ha finalizado el estacionamiento por que usted elijio el modo Manual"
					+ "Procure finalizar el estacionamiento para evitar descontar saldo de mas");
			System.out.print("Alerta: No se ha finalizado el estacionamiento por que usted elijio el modo Manual"
					+ "Procure finalizar el estacionamiento para evitar descontar saldo de mas");
		}
	}

	@Override
	public boolean estaEnModoAutomatico() {
		return false;
	}


	@Override
	protected void darRespuesta(AplicacionSEM app) {
		System.out.print("!---------------------------------!"
				 + "Su estacionamiento fue dado de alta con exito."
				 + "Hora exacta: " + app.getHoraActual()
				 + "Hora fin: " + "--"
				 + "Esta en Modo Manual, usted debe encargarse de finalizar el estacionamiento."
				 + "De lo contrario, la hora de fin quedara establecida hasta que:"
				 + "Sean las 20:00."
				 + "Usted mismo finalize el estacionamiento antes de las 20:00,"
				 + "descontando de su saldo el credito disponible segun la cantidad de horas que estuvo"
				 + "o de lo contrario descontando saldo de mas si no alcanza a cubrir el equivalente de"
				 + "saldo acreditado con la cantidad de horas que dejo su coche"
				 + "Notese que le quedara saldo negativo para esta ultima opcion"
				 + "!---------------------------------!");
	}


	@Override
	protected void avisoDeCambio() {
		System.out.print("!---------------------------------!"
				+ "Usted a elegido el Modo Manual");
	}


	
	
}

package clasesWalle;


public class ModoAutomatico extends Modo{

	
	@Override
	public void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			//app.getUsuario().notificarAlerta("Alerta: El estacionamiento se va a iniciar automaticamente");
			System.out.print("Alerta: Se ha iniciado el estacionamiento con exito!"
					+ "Recuerde que esta activado el Modo Automatico");
		}
	}

	@Override
	public void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			//app.getUsuario().notificarAlerta("Alerta: El estacionamiento se va a finalizar automaticamente");
			System.out.print("Alerta: Se ha finalizado el estacionamiento con exito!"
					+ "Recuerde que esta activado el Modo Automatico");
		}
	}

	@Override
	public boolean estaEnModoAutomatico() {
		return true;
	}

	@Override
	public void avisoDeCambio() {
		System.out.print("Usted a elegido el Modo Automatico");
	}

	@Override
	public void darRespuestaInicial(AplicacionSEM app) {
		System.out.print("!---------------------------------!"
				 + "Su estacionamiento fue dado de alta con exito."
				 + "Hora exacta: " + app.getHoraInicio()
				 + "Hora fin: " + "Pendiente"
				 + "La hora fin quedara establecida segun la cantidad de horas maximas equivalentes a su saldo acreditado"
				 + "Esta en Modo Automatico, lo que significa que una vez se consuman las horas equivalentes al saldo acreditado"
				 + "El estacionamiento se dara de baja automaticamente"
				 + "!---------------------------------!");
	}
	
	@Override
	public void darRespuestaFinal(AplicacionSEM app) {
		System.out.print("!---------------------------------!"
				 + "                                        "
				 + "Su estacionamiento fue dado de baja con exito."
				 + "Hora exacta: " + app.getHoraInicio()
				 + "Hora fin: " + app.horaFinal()
				 + "Duracion: " + (app.horaFinal() - app.getHoraInicio())
				 + "Costo: " + app.calcularCreditoAPagar()
				 + "Esta en Modo Automatico, lo que significa que fue dado de baja automaticamente"
				 + "!---------------------------------!");
	
	}
}

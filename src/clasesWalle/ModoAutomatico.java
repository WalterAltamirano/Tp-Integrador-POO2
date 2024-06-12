package clasesWalle;

import clasesIan.Estacionamiento;
import clasesIan.EstacionamientoAplicacion;

public class ModoAutomatico extends Modo{

	@Override
	public void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente) {
		try {
			this.puedeEstacionar(app, patente);
			app.getSistemaEstacionamiento()
			.registrarEstacionamiento(new EstacionamientoAplicacion(numeroDeCelular,patente));
	        this.darRespuesta(app);
		}
		catch (Exception e) {
		}
		  
	}

	@Override
	public void finDeEstacionamiento(AplicacionSEM app,int numeroDeCelular) {
		app.descontarSaldo();
		app.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
		this.notificarAlertaDeFinDeEstacionamiento(app);
	}

	@Override
	public void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			app.getUsuario().notificarAlerta("Alerta: El estacionamiento se va a iniciar automaticamente");
			System.out.print("Alerta: Se ha iniciado el estacionamiento con exito!"
					+ "Recuerde que esta activado el Modo Automatico");
		}
	}

	@Override
	public void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app) {
		if(app.getGps().estaEncendido()) {
			app.getUsuario().notificarAlerta("Alerta: El estacionamiento se va a finalizar automaticamente");
			System.out.print("Alerta: Se ha finalizado el estacionamiento con exito!"
					+ "Recuerde que esta activado el Modo Automatico");
		}
	}

	@Override
	public boolean estaEnModoAutomatico() {
		return true;
	}

	@Override
	protected void avisoDeCambio() {
		System.out.print("Usted a elegido el Modo Automatico");
	}

	@Override
	protected void darRespuesta(AplicacionSEM app) {
		System.out.print("!---------------------------------!"
				 + "Su estacionamiento fue dado de alta con exito."
				 + "Hora exacta: " + app.getHoraActual()
				 + "Hora fin: " + "--"
				 + "La hora fin quedara establecida segun la cantidad de horas maximas equivalentes a su saldo acreditado"
				 + "Esta en Modo Automatico, lo que significa que una vez se consuman las horas equivalentes al saldo acreditado"
				 + "El estacionamiento se dara de baja automaticamente"
				 + "!---------------------------------!");
	}
	
	
}

package clasesWalle;

import clasesIan.EstacionamientoAplicacion;

public abstract class Modo {
	
	//Templeate Method 1
	public void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente)  {
		if(app.tieneCreditoSuficienteParaEstacionar() && app.estaEnZonaDeEstacionamiento()
				&& !app.hayEstacionamientoCon(patente)) {
			app.getSistemaEstacionamiento()
			.registrarEstacionamiento(new EstacionamientoAplicacion(numeroDeCelular,patente));
			this.notificarAlertaDeInicioDeEstacionamiento(app);
			this.darRespuestaInicial(app);
		}
		else {
			System.out.print("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
					+ "Saldo: " + app.getCredito() + "Esta una zona valida: " 
					+ app.estaEnZonaDeEstacionamiento() + "El auto ya esta estacionado: " 
					+ app.hayEstacionamientoCon(patente));
		}
	}
	//Templeate Method 2
	public void finDeEstacionamiento(AplicacionSEM app,int numeroDeCelular) {
		if(!app.hayEstacionamientoCon(numeroDeCelular)) {
			 app.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
			 this.notificarAlertaDeFinDeEstacionamiento(app);
			 this.darRespuestaFinal(app);
			 app.descontarSaldo();
		}
		
	}
	
	public abstract void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app);
	
	public abstract void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app);
	
	public abstract boolean estaEnModoAutomatico();
	
	//Operacion Concreta
//	public void puedeEstacionar(AplicacionSEM app,String patente) {
//		if(!app.tieneCreditoSuficienteParaEstacionar() || !app.estaEnZonaDeEstacionamiento()
//				|| app.hayEstacionamientoCon(patente)) {
//			System.out.print("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
//					+ "Saldo: " + app.getCredito() + "Esta una zona valida: " 
//					+ app.estaEnZonaDeEstacionamiento() + "El auto ya esta estacionado: " 
//					+ app.hayEstacionamientoCon(patente));
//		}
//	}
	
	public abstract void avisoDeCambio();
	
	public abstract void darRespuestaInicial(AplicacionSEM app);
	
	public abstract void darRespuestaFinal(AplicacionSEM app);
	
}

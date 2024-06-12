package clasesWalle;

import clasesIan.EstacionamientoAplicacion;

public abstract class Modo {
	
	public void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente)  {
		try {
			this.puedeEstacionar(app,patente);
			app.getSistemaEstacionamiento()
			.registrarEstacionamiento(new EstacionamientoAplicacion(numeroDeCelular,patente));
			this.darRespuestaInicial(app);
		}
		catch (Exception e) {
		}
	}
	
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
	
	protected void puedeEstacionar(AplicacionSEM app,String patente) throws Exception {
		if(!app.tieneCreditoSuficienteParaEstacionar() || app.estaEnZonaDeEstacionamiento()
				|| app.hayEstacionamientoCon(patente)) {
			
			throw new Exception("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
					+ "Saldo: " + app.getCredito() + "Esta una zona valida: " 
					+ app.estaEnZonaDeEstacionamiento() + "El auto ya esta estacionado: " 
					+ app.hayEstacionamientoCon(patente));
		}
	}
	
	protected abstract void avisoDeCambio();
	
	protected abstract void darRespuestaInicial(AplicacionSEM app);
	
	protected abstract void darRespuestaFinal(AplicacionSEM app);
	
}

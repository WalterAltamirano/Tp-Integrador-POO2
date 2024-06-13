package clasesWalle;

import clasesIan.EstacionamientoAplicacion;

public abstract class Modo {
	
	//Templeate Method 1
	public final void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente)  {
		try {
			this.puedeEstacionar(app,patente);
			app.getSistemaEstacionamiento()
			.registrarEstacionamiento(new EstacionamientoAplicacion(numeroDeCelular,patente));
			this.notificarAlertaDeInicioDeEstacionamiento(app);
			this.darRespuestaInicial(app);
		}
		catch (Exception e) {
		}
	}
	//Templeate Method 2
	public final void finDeEstacionamiento(AplicacionSEM app,int numeroDeCelular) {
		if(!app.hayEstacionamientoCon(numeroDeCelular)) {
			 app.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
			 this.notificarAlertaDeFinDeEstacionamiento(app);
			 this.darRespuestaFinal(app);
			 app.descontarSaldo();
		}
		
	}
	
	//Operacion Primitiva -- Paso del algoritmo (TM 1)
	protected abstract void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app);
	//Operacion Primitiva -- Paso del algoritmo (TM 2)
	protected abstract void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app);
	
	public abstract boolean estaEnModoAutomatico();
	
	//Operacion Concreta
	public void puedeEstacionar(AplicacionSEM app,String patente) throws Exception {
		if(!app.tieneCreditoSuficienteParaEstacionar() || app.estaEnZonaDeEstacionamiento()
				|| app.hayEstacionamientoCon(patente)) {
			throw new Exception("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
					+ "Saldo: " + app.getCredito() + "Esta una zona valida: " 
					+ app.estaEnZonaDeEstacionamiento() + "El auto ya esta estacionado: " 
					+ app.hayEstacionamientoCon(patente));
		}
	}
	
	protected abstract void avisoDeCambio();
	
	//Operacion Primitiva -- Paso del algoritmo (TM 1)
	protected abstract void darRespuestaInicial(AplicacionSEM app);
	//Operacion Primitiva -- Paso del algoritmo (TM 2)
	protected abstract void darRespuestaFinal(AplicacionSEM app);
	
}

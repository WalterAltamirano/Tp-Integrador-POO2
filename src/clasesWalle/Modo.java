package clasesWalle;

import clasesIan.EstacionamientoAplicacion;

public abstract class Modo {
	
	//Templeate Method 1
	public void inicioDeEstacionamiento(AplicacionSEM app,Integer numeroDeCelular, String patente)  {
		try {	
			this.puedeEstacionar(app,patente);
		}
		catch(ExcepcionPersonalizada e) {
			e.printStackTrace();
		}
		app.getSistemaEstacionamiento()
		.registrarEstacionamiento(app.instanciaDeEstacionamiento(numeroDeCelular, patente));
		this.notificarAlertaDeInicioDeEstacionamiento(app);
		this.darRespuestaInicial(app);
 	}
	//Templeate Method 2
	public void finDeEstacionamiento(AplicacionSEM app,Integer numeroDeCelular) {
		if(!app.hayEstacionamientoCon(numeroDeCelular)) {
			 app.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
			 this.notificarAlertaDeFinDeEstacionamiento(app);
			 this.darRespuestaFinal(app);
			 app.descontarSaldo();
		}
	
	}
	
	//Operacion Concreta
	public void puedeEstacionar(AplicacionSEM app,String patente) throws ExcepcionPersonalizada {
		if(!app.tieneCreditoSuficienteParaEstacionar()) {
			throw new ExcepcionPersonalizada("No hay credito suficiente");
		}
		if(!app.estaEnZonaDeEstacionamiento()) {
			throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
		}
			
		if(app.hayEstacionamientoCon(patente)) {
			throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
		}
	}
	
	
	public abstract void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app);
	
	public abstract void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app);
	
	public abstract void avisoDeCambio();
	
	public abstract void darRespuestaInicial(AplicacionSEM app);
	
	public abstract void darRespuestaFinal(AplicacionSEM app);
	
}

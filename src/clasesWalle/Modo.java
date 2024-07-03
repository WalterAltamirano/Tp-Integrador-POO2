package clasesWalle;


//DEFINE PROTOCOLO POR AHORA, LO QUE SIGNIFICA QUE DEBERIA SER UNA INTERFACE!!!!!!!!!

public interface Modo {
	
	public abstract void inicioDeEstacionamiento(AplicacionSEM app);
	public abstract void finDeEstacionamiento(AplicacionSEM app);
	public abstract void avisoDeCambio();
}

//	public void inicioDeEstacionamiento(AplicacionSEM app,Integer numeroDeCelular, String patente)  {
//		try {	
//			this.puedeEstacionar(app,patente);
//		}
//		catch(ExcepcionPersonalizada e) {
//			e.printStackTrace();
//		}
//		app.getSistemaEstacionamiento()
//		.registrarEstacionamiento(app.instanciaDeEstacionamiento(numeroDeCelular, patente));
//		this.notificarAlertaDeInicioDeEstacionamiento(app);
//		this.darRespuestaInicial(app);
// 	}
//	public void finDeEstacionamiento(AplicacionSEM app,Integer numeroDeCelular) {
//		if(!app.hayEstacionamientoCon(numeroDeCelular)) {
//			 app.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
//			 this.notificarAlertaDeFinDeEstacionamiento(app);
//			 this.darRespuestaFinal(app);
//			 app.descontarSaldo();
//		}
//	
//	}
//	
//	public void puedeEstacionar(AplicacionSEM app,String patente) throws ExcepcionPersonalizada {
//		if(!app.tieneCreditoSuficienteParaEstacionar()) {
//			throw new ExcepcionPersonalizada("No hay credito suficiente");
//		}
//		if(!app.estaEnZonaDeEstacionamiento()) {
//			throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
//		}
//			
//		if(app.hayEstacionamientoCon(patente)) {
//			throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
//		}
//	}
//	
//	
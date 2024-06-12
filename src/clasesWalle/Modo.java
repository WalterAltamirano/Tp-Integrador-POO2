package clasesWalle;

public abstract class Modo {
	
	public abstract void inicioDeEstacionamiento(AplicacionSEM app,int numeroDeCelular, String patente);
	
	public abstract void finDeEstacionamiento(AplicacionSEM app,int numeroDeCelular);
	
	public abstract void notificarAlertaDeInicioDeEstacionamiento(AplicacionSEM app);
	
	public abstract void notificarAlertaDeFinDeEstacionamiento(AplicacionSEM app);
	
	public abstract boolean estaEnModoAutomatico();
	
	protected void puedeEstacionar(AplicacionSEM app,String patente) throws Exception {
		if(!app.tieneCreditoSuficiente() || app.estaEnZonaDeEstacionamiento()
				|| app.hayEstacionamientoCon(patente)) {
			
			throw new Exception("No se cumplen alguna/as de las condiciones para iniciar un estacionamiento"
					+ "Saldo: " + app.getCredito() + "Esta una zona valida: " 
					+ app.estaEnZonaDeEstacionamiento() + "El auto ya esta estacionado: " 
					+ app.hayEstacionamientoCon(patente));
		}
	}
	
	protected abstract void avisoDeCambio();
	
	protected abstract void darRespuesta(AplicacionSEM app);
	
}

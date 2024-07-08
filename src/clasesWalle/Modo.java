package clasesWalle;

public interface Modo {
	
	public void avisoDeCambio();
	public void inicioDeEstacionamiento(AplicacionSEM app) ;
	public void finDeEstacionamiento(AplicacionSEM app);
	public void puedeAlertarInicio(AplicacionSEM app ) throws ExcepcionPersonalizada;
	public void puedeAlertarFin(AplicacionSEM app) throws ExcepcionPersonalizada;
	
}


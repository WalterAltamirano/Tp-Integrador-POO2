package estrategiasAplicacion;

import aplicacionSEM.AplicacionSEM;

public interface Modo {
	
	public abstract void avisoDeCambio();
	public abstract void inicioDeEstacionamiento(AplicacionSEM app) ;
	public abstract void finDeEstacionamiento(AplicacionSEM app);
	
}


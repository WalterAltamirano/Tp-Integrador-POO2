package estadosAplicacion;

import aplicacionSEM.AplicacionSEM;

public interface EstadoApp {
	
	public abstract void manejando(AplicacionSEM aplicacion);
	public abstract void caminando(AplicacionSEM aplicacion);
}

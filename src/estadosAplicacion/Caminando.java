package estadosAplicacion;

import aplicacionSEM.AplicacionSEM;

public class Caminando implements EstadoApp{
	
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a manejando");
		aplicacion.setEstado(new EnAuto());
		aplicacion.pasoAAuto();
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es caminando");
	}
}

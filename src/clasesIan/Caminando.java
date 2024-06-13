package clasesIan;

import clasesWalle.AplicacionSEM;

public class Caminando extends EstadoApp{
	
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a caminando");
		aplicacion.setEstado(new Caminando());
		if(aplicacion.getModo().estaEnModoAutomatico()) {
			aplicacion.finalizarEstacionamiento(aplicacion.getNumeroDeCelular());
		}
		aplicacion.alertaFinDeEstacionamiento();
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es caminando");
	}
}

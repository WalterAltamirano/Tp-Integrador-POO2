package clasesIan;

import clasesWalle.AplicacionSEM;

public class EnAuto extends EstadoApp {

	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es en auto");
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a caminando");	
		aplicacion.setEstado(new Caminando());
		if(aplicacion.getModo().estaEnModoAutomatico()) {
			aplicacion.inicioEstacionamiento(aplicacion.getNumeroDeCelular(), aplicacion.getUsuario().getPatente());
		}
		aplicacion.alertaInicioDeEstacionamiento();
	}
}

package estadosAplicacion;

import aplicacionSEM.AplicacionSEM;

public class EnAuto implements EstadoApp {

	//Metodos implementados de interfaz
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es en auto");
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a caminando");	
		aplicacion.setEstado(new Caminando());
		aplicacion.pasoACaminando();
	}
}
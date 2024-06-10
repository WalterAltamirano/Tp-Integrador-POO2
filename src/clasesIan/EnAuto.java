package clasesIan;

public class EnAuto extends EstadoApp {

	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es en auto");
	}

	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a manejando");
		aplicacion.setEstado(new Caminando());
	}

}

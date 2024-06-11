package clasesIan;

public class Caminando extends EstadoApp{
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a caminando");
		aplicacion.setEstado(new Caminando());
		aplicacion.finalizarEstacionamiento(aplicacion.getNumero());
		aplicacion.alertaFinDeEstacionamiento();
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es caminando");
	}
}

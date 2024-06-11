package clasesIan;

public class Caminando extends EstadoApp{
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a caminando");
		aplicacion.setEstado(new Caminando());
		aplicacion.finalizarEstacionamiento(aplicacion.getUsuario().getNumero());
		
		if(aplicacion.getModo().esAutomatico() && aplicacion.estaEncendido()){
			aplicacion.finalizarEstacionamiento(aplicacion.getNumero());
			aplicacion.alertaFinDeEstacionamiento();
		}else if(aplicacion.getModo().esAutomatico() && !aplicacion.estaEncendido()){
			 System.out.println("Para utilizar el modo automatico debe encender el GPS");
		}else if(aplicacion.getModo().esManual() && aplicacion.estaEncendido()){
			aplicacion.alertaFinDeEstacionamiento();
		}else {
			System.out.println("Para finalizar el estacionamiento el modo debe estar en manual o automatico y el GPS activado");
		}
	}

	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es caminando");
	}
}

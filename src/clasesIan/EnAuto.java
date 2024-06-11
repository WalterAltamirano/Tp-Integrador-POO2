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
		
		if(aplicacion.getModo().esAutomatico() && aplicacion.estaEncendido()){
			aplicacion.inicioEstacionamiento(aplicacion.getNumero(), aplicacion.getUsuario().getPatente());
			aplicacion.alertaInicioDeEstacionamiento();
		}else if(aplicacion.getModo().esAutomatico() && !aplicacion.estaEncendido()){
			 System.out.println("Para utilizar el modo automatico debe encender el GPS");
		}else if(aplicacion.getModo().esManual() && aplicacion.estaEncendido()){
			aplicacion.alertaInicioDeEstacionamiento();
		}else {
			System.out.println("Para registrar el estacionamiento el modo debe estar en manual o automatico y el GPS activado");
		}
	}
}

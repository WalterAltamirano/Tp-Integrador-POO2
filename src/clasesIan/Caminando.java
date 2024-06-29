package clasesIan;
import clasesWalle.AplicacionSEM;
import clasesWalle.ModoAutomatico;

public class Caminando extends EstadoApp{
	@Override
	public void manejando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual cambiara a manejando");
		aplicacion.setEstado(new Caminando());
		//MODIFICACION:
		aplicacion.pasoAAuto();
	}
	@Override
	public void caminando(AplicacionSEM aplicacion) {
		System.out.println("El estado actual es caminando");
	}
}

/* 
 * EN MODO GENERAL SERIAN ABSTRACTOS.
 * IRIA EN CADA MODO:
  EN MODO AUTOMATICO:
	  public void finDeEstacionamiento(){
	  	aplicacion.finalizarEstacionamiento(aplicacion.getNumeroDeCelular());
	  }
	  public void inicioEstacionamiento(){
	  	aplicacion.iniciarEstacionamiento(aplicacion.getNumeroDeCelular(), aplicacion.getUsuario().getPatente())
	  }
  
  ---------------------------------------
  
  EN MODO MANUAL:
	  public void finDeEstacionamiento(){
	  	System.out.println("Deberias finalizar el estacionamiento!");
	  }
	  
	  public void inicioEstacionamiento(){
	  	System.out.println("Deberias iniciar el estacionamiento!");
	  }
*/




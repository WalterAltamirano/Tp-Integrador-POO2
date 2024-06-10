package clasesWalle;

public class EnAuto extends EstadoApp {

	@Override
	public void caminando(AplicacionSEM app) {
		
		app.setEstado(new Caminando());
		
	}

	@Override
	public void manejando(AplicacionSEM app) {
		// TODO Auto-generated method stub
		
	}	

	
}

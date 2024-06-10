package clasesIan;

public class EstacionamientoAplicacion extends Estacionamiento{
	private int origenCelular;
	
	public EstacionamientoAplicacion(int numero, String patente) {
		this.origenCelular = numero;
		this.patenteDeVehiculo = patente; //en la super.
	}
	
	public int getNumeroDeCelularDeEstacionamiento() {
		return this.origenCelular;
	}
}

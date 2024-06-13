package clasesIan;

import java.time.LocalDateTime;

public class EstacionamientoAplicacion extends Estacionamiento{
	
	private int origenCelular;
	
	public EstacionamientoAplicacion(int numero, String patente) {
		this.origenCelular = numero;
		this.patenteDeVehiculo = patente; //en la super.
		this.horaDeInicio = LocalDateTime.now();
	}
	
	public int getNumeroDeCelularDeEstacionamiento() {
		return this.origenCelular;
	}
	
	public void setHoraDeFin(LocalDateTime hora) {
		this.horaDeFin = hora;
	}
}

package estacionamiento;

import java.time.LocalDateTime;

public class EstacionamientoAplicacion extends Estacionamiento{
	
	private int origenCelular;
	
	//Es usado en AplicacionSEM para la generacion del mismo
	//Constructor
	public EstacionamientoAplicacion(int numero, String patente) {
		this.origenCelular = numero;
		this.patenteDeVehiculo = patente;
		this.horaDeInicio = LocalDateTime.now();
	}
	
	//--------------------------------------------------------
	public int getNumeroDeCelularDeEstacionamiento() {
		return this.origenCelular;
	}
	
	@Override
	public int getHoraFin() {
		
		return horaDeFin.getHour();
	}
	@Override
	public int getHoraInicio() {
		
		return horaDeInicio.getHour();
	}
	
	@Override
	public void setHoraDeFin(LocalDateTime hora) {
		this.horaDeFin = hora;
	}
	
	public void setHoraDeInicio(LocalDateTime hora) {
		this.horaDeInicio = hora;
	}
	
}

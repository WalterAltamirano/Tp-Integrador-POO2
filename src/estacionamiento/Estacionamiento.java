package estacionamiento;

import java.time.*;

public abstract class Estacionamiento {
	protected LocalDateTime horaDeInicio;
	protected LocalDateTime horaDeFin;
	protected String patenteDeVehiculo;
	private boolean vigencia;
	
	public boolean estaVigente() {
		if(LocalDateTime.now().isAfter(horaDeInicio) && LocalDateTime.now().isBefore(horaDeFin)) {
			vigencia = true;
		}else{
			vigencia = false;
		}
		return vigencia;
	}
	
	public void notificarFinalizacion() {
        if (haFinalizado()) {
            System.out.println("El estacionamiento ha finalizado para el vehículo con patente: " + patenteDeVehiculo);
        }
        
	}

	private boolean haFinalizado() {
		return LocalDateTime.now().isAfter(horaDeFin);
	}
	
	public void finalizarEstacionamiento() {
		vigencia = false;
	}
	
	public String getPatente() {
		return patenteDeVehiculo;
	}
	
	public int getNumeroDeCelularDeEstacionamiento() {
		return 0;
	}
	
	public boolean getViencia() {
		return vigencia;
	}
	
	public void setHoraDeFin(LocalDateTime hora) {
		
	}
	
	public int getHoraFin() {
		
		return 0;
	}

	public int getHoraInicio() {
		
		return 0;
	}
}

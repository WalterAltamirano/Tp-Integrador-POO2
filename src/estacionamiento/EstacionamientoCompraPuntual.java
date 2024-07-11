package estacionamiento;

import java.time.LocalDateTime;

import puntoDeVentaYCompras.CompraPuntual;

public class EstacionamientoCompraPuntual extends Estacionamiento{

	private CompraPuntual compraPuntual;
	
	//Es usado en PuntoDeVenta para la generacion del mismo
	//Constructor
	public EstacionamientoCompraPuntual(CompraPuntual compraPuntual, String patente) {
		this.compraPuntual = compraPuntual;
		this.patenteDeVehiculo = patente;
		this.horaDeInicio = LocalDateTime.now();
		long horasCompradas = (long) compraPuntual.getHorasCompradas();
		this.horaDeFin = this.horaDeInicio.plusHours(horasCompradas);
	}
	
}

package clasesIan;

import clasesMatias.CompraPuntual;

public class EstacionamientoCompraPuntual extends Estacionamiento{

	private CompraPuntual compraPuntual;
	
	public EstacionamientoCompraPuntual(CompraPuntual compraPuntual, String patente) {
		this.compraPuntual = compraPuntual;
		this.patenteDeVehiculo = patente;
	}
	
}

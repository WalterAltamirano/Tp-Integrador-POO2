package clasesMatias;

import clasesIan.Auto;
import clasesIan.Estacionamiento;
import clasesIan.EstacionamientoCompraPuntual;
import clasesWalle.AplicacionSEM;

public class PuntoDeVenta {
	
	private SEM sem;

	public SEM getSem() {
		return sem;
	}

	public void setSem(SEM sem) {
		this.sem = sem;
	}

	public void cargarSaldo(AplicacionSEM aplicacion, double saldo) {
		aplicacion.cargarSaldo(saldo);
		Compra nuevaCarga = new CargaDeSaldo(this, saldo, aplicacion);
		this.getSem().registrarCompra(nuevaCarga);
	}

	public void registrarEstacionamiento(double horaInicio, double horaFin, Auto auto) {
		
		Compra nuevaCompraEstacionamiento = new CompraPuntual(this, horaInicio);
		this.getSem().registrarCompra(nuevaCompraEstacionamiento);
		
		Estacionamiento nuevoEstacionamiento = new EstacionamientoCompraPuntual(auto, horaInicio, horaFin, nuevaCompraEstacionamiento);
		this.getSem().registrarEstacionamiento(nuevoEstacionamiento);
	}

}

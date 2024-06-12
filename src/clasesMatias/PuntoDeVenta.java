package clasesMatias;


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
		CargaDeSaldo nuevaCarga = this.generarCompraSaldo(saldo, aplicacion);
		this.getSem().registrarCompra(nuevaCarga);
		this.getSem().notificarOrganismosInteresados();
		
	}
	
	public CargaDeSaldo generarCompraSaldo(double saldo, AplicacionSEM aplicacion) {
		CargaDeSaldo nuevaCarga = new CargaDeSaldo(this, saldo, aplicacion);
		return nuevaCarga;
	}

	public void registrarEstacionamiento(String patente, int cantidadDeHoras ) {
		
		CompraPuntual nuevaCompraEstacionamiento = this.generarNuevaCompra(cantidadDeHoras);
	    this.generarNuevoEstacionamiento(nuevaCompraEstacionamiento, patente);
		this.getSem().notificarOrganismosInteresados();
		
	}
	
	public CompraPuntual generarNuevaCompra(int cantidadDeHoras) {
		CompraPuntual nuevaCompraEstacionamiento = new CompraPuntual(this, cantidadDeHoras);
		this.getSem().registrarCompra(nuevaCompraEstacionamiento);
		return nuevaCompraEstacionamiento;
	}
	
	public EstacionamientoCompraPuntual generarNuevoEstacionamiento(CompraPuntual nuevaCompraEstacionamiento, String patente) {
		EstacionamientoCompraPuntual nuevoEstacionamiento = new EstacionamientoCompraPuntual(nuevaCompraEstacionamiento, patente);
		this.getSem().registrarEstacionamiento(nuevoEstacionamiento);
		return nuevoEstacionamiento;
	}
	

}

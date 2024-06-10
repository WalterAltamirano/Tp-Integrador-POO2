package clasesIan;

public class Usuario {
	Auto auto;
	AplicacionSEM aplicacion;
	
	public Usuario(Auto auto, AplicacionSEM aplicacion) {
		this.auto = auto;
		this.aplicacion = aplicacion;
	}
	
	//FALTA RETOCAR
	public void estacionamientoCompraPuntual() {
	}
	
	public void solicitarCargaDeSaldo(PuntoDeVenta puestoDeCarga, double saldoACargar) {
		puestoDeCarga.cargarSaldo(aplicacion, saldoACargar);
	}
	
	//Dudoso
	public void estacionamientoPorAplicacion() {
		this.aplicacion.inicioEstacionamiento(aplicacion.getNumero(), auto.getPatente());
	}
}

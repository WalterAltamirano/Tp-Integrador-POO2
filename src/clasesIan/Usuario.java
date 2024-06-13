package clasesIan;

import clasesMatias.PuntoDeVenta;
import clasesWalle.AplicacionSEM;

public class Usuario {
	Auto auto;
	AplicacionSEM aplicacion;
	
	public Usuario(Auto auto, AplicacionSEM aplicacion) {
		this.auto = auto;
		this.aplicacion = aplicacion;
	}
	
	public void estacionamientoCompraPuntual(String patente, int cantidadDeHoras, PuntoDeVenta puntoDeVenta) {
		puntoDeVenta.registrarEstacionamiento(patente, cantidadDeHoras);
	}
	
	public void solicitarCargaDeSaldo(PuntoDeVenta puestoDeCarga, double saldoACargar) {
		puestoDeCarga.cargarSaldo(aplicacion, saldoACargar);
	}
	
	//Dudoso
	public void estacionamientoPorAplicacion() {
		this.aplicacion.inicioEstacionamiento(aplicacion.getNumeroDeCelular(), auto.getPatente());
	}
	
	public void notificarAlerta(String alerta) {
		System.out.println(alerta);
	}
	
	public String getPatente() {
		return auto.getPatente();
	}
}

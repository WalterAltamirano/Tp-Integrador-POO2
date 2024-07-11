package dataUsuario;

import aplicacionSEM.AplicacionSEM;
import puntoDeVentaYCompras.PuntoDeVenta;

public class Usuario {
	Auto auto;
	AplicacionSEM aplicacion;
	
	//Constructor
	public Usuario(Auto auto, AplicacionSEM aplicacion) {
		this.auto = auto;
		this.aplicacion = aplicacion;
	}
	
	//En un punto de venta, punto de venta se encarga de registrarlo.
	public void estacionamientoCompraPuntual(String patente, int cantidadDeHoras, PuntoDeVenta puntoDeVenta) {
		puntoDeVenta.registrarEstacionamiento(patente, cantidadDeHoras);
	}
	
	public void solicitarCargaDeSaldo(PuntoDeVenta puestoDeCarga, double saldoACargar) {
		puestoDeCarga.cargarSaldo(aplicacion, saldoACargar);
	}
	
	//A traves de la aplicacion
	public void estacionamientoPorAplicacion(int horaInicio) {
		this.aplicacion.iniciarEstacionamiento(horaInicio);
	}

	public String getPatente() {
		return auto.getPatente();
	}
	
	public AplicacionSEM getAplicacion() {
		return aplicacion;
	}
}

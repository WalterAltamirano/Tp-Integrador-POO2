package dataUsuario;

import aplicacionSEM.AplicacionSEM;
import puntoDeVentaYCompras.PuntoDeVenta;

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
	public void estacionamientoPorAplicacion(int horaInicio) {
		this.aplicacion.iniciarEstacionamiento(horaInicio);
	}
	
	/*public void notificarAlerta(String alerta) {
		System.out.println(alerta);
	}*/
	
	public String getPatente() {
		return auto.getPatente();
	}
	
	public AplicacionSEM getAplicacion() {
		return aplicacion;
	}
}

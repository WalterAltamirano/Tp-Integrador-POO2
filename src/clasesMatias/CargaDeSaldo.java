package clasesMatias;

import clasesWalle.AplicacionSEM;

public class CargaDeSaldo extends Compra {
	
	private double montoCargado;
	private int numeroCelularCargado;

	public CargaDeSaldo(PuntoDeVenta puntoDeVenta, double saldo, AplicacionSEM aplicacion) {
		super(puntoDeVenta);
		
		this.montoCargado = saldo;
		this.numeroCelularCargado = aplicacion.getNumeroDeCelular();
		
	}


		
		
		
	



}

package clasesMatias;

import clasesWalle.AplicacionSEM;

/** Clase que representa las Compras de Carga de Saldo
 * 
 *  Autor: MazaVega Matias
 * */

public class CargaDeSaldo extends Compra {
	
	private double montoCargado;
	private int numeroCelularCargado;
    /*
     * Se inicializa recibiendo un PuntoDeVenta, un doubl representando el saldo cargado 
     * y la AplicacionSEM en la cual se suma dicho saldo
     * */
	public CargaDeSaldo(PuntoDeVenta puntoDeVenta, double saldo, AplicacionSEM aplicacion) {
		super(puntoDeVenta);
		
		this.montoCargado = saldo;
		this.numeroCelularCargado = aplicacion.getNumeroDeCelular();
		
	}
    //retorna el monto cargado
	public double getMontoCargado() {
		return montoCargado;
	}


		
		
		
	



}

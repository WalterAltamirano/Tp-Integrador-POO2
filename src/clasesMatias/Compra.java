package clasesMatias;

import java.util.Date;

public abstract class Compra {

	private String numeroDeControl;
	private PuntoDeVenta puntoDeVenta;
	protected Date fecha;
	protected long hora;

	public Compra(PuntoDeVenta puntoDeVenta) {
		this.puntoDeVenta = puntoDeVenta;
		this.numeroDeControl = "random number";
		this.fecha = new Date();	
	}


	

	

}

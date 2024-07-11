package puntoDeVentaYCompras;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/** Clase abstracta que engloba las clases de Compra que se pueden hacer en un PuestoDeVenta
 * 
 *  Autor: MazaVega Matias
 * */

public abstract class Compra {

	private String numeroDeControl;
	private PuntoDeVenta puntoDeVenta;
	protected String fecha;
	protected long horaEmision;
/*
 * El sistema para determinar el nuneroDeControl no esta implementado
 * 
 * */
	public Compra(PuntoDeVenta puntoDeVenta) {
		this.puntoDeVenta = puntoDeVenta;
		this.numeroDeControl = "random number";
		this.fecha = LocalDate.now().toString();
		this.horaEmision = LocalDateTime.now().getHour();
	}


	

	

}

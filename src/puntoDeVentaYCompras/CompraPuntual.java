package puntoDeVentaYCompras;

/** Clase que representa las Compras de horas de Estacionamiento por CompraPuntual
 * 
 *  Autor: MazaVega Matias
 * */

public class CompraPuntual extends Compra {

	private int horasCompradas;
	private long horaInicio;
	private long horaFin;
	/*
	 * Se inicializa recibiendo un PuntoDeVenta y una cantidad de horas compradas
	 * La horaDeFin se calcula al sumar el valor de las horas compradas al valor de la hora de Inicio
	 * */
	public CompraPuntual(PuntoDeVenta puntoDeVenta, int cantidadDeHoras) {
		super(puntoDeVenta);
		this.horasCompradas = cantidadDeHoras;
		this.horaInicio = this.horaEmision;
		this.horaFin = this.horaInicio + this.horasCompradas;
				
	}
	
	
	public int getHorasCompradas() {
		return horasCompradas;
	}


}

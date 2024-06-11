package clasesMatias;

public class CompraPuntual extends Compra {

	private int horasCompradas;
	private long horaInicio;
	private long horaFin;

	public CompraPuntual(PuntoDeVenta puntoDeVenta, int cantidadDeHoras) {
		super(puntoDeVenta);
		this.horasCompradas = cantidadDeHoras;
		this.horaInicio = this.horaEmision;
		this.horaFin = this.horaInicio + this.horasCompradas;
		
		
	}


}

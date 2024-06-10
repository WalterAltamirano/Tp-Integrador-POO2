package clasesMatias;

public class CompraPuntual extends Compra {

	private double horasCompradas;

	public CompraPuntual(PuntoDeVenta puntoDeVenta, double horaInicio, double horaFin) {
		super(puntoDeVenta);
		this.hora = (long) horaInicio;
		this.horasCompradas = horaFin - horaInicio;
	}


}

package clasesMatias;

public class CompraPuntual extends Compra {

	public CompraPuntual(PuntoDeVenta puntoDeVenta, double horaInicio) {
		super(puntoDeVenta);
		this.hora = (long) horaInicio;
	}


}

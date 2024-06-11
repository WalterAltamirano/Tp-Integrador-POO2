package clasesMatias;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public abstract class Compra {

	private String numeroDeControl;
	private PuntoDeVenta puntoDeVenta;
	protected String fecha;
	protected long horaEmision;

	public Compra(PuntoDeVenta puntoDeVenta) {
		this.puntoDeVenta = puntoDeVenta;
		this.numeroDeControl = "random number";
		this.fecha = LocalDate.now().toString();
		this.horaEmision = LocalDateTime.now().getHour();
	}


	

	

}

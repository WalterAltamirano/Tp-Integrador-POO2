package clasesMatias;

import java.util.Date;



public class Infraccion {
	
	private String patenteAutoEnInfraccion;
	private Date fecha;
	private long hora;
	private Inspector inspector;
	private ZonaDeEstacionamiento zona;

	public Infraccion(String patente, Inspector inspector) {
		this.patenteAutoEnInfraccion = patente;
		this.fecha = new Date();	
		this.hora = fecha.getTime();
		this.inspector = inspector;
		this.zona = inspector.getZonaAsignada();
	}

	public String getPatenteAutoEnInfraccion() {
		return patenteAutoEnInfraccion;
	}

}

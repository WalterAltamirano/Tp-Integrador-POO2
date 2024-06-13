package clasesMatias;

import java.util.Date;

/** Clase que representa las Infracciones emitidas por el Inspector para autos estacionados 
 * sin un Estacionamiento vigente registrado en la lista de Estacionamientos del SEM
 * 
 *  Autor: MazaVega Matias
 * */

public class Infraccion {
	
	private String patenteAutoEnInfraccion;
	private Date fecha;
	private long hora;
	private Inspector inspector;
	private ZonaDeEstacionamiento zona;

	/*
	 * Se inicializa recibiendo la petente de un Auto como String y un Inspector
	 * La fecha y la hora se determinan con la hora actual al momento de la creacion
	 * La ZonaDeEstacionamiento se determina a partir de la zona asignada al Inspector
	 * */
	public Infraccion(String patente, Inspector inspector) {
		this.patenteAutoEnInfraccion = patente;
		this.fecha = new Date();	
		this.hora = fecha.getTime();
		this.inspector = inspector;
		this.zona = inspector.getZonaAsignada();
	}
   // Devuelve la patente del Auto en infraccion
	public String getPatenteAutoEnInfraccion() {
		return patenteAutoEnInfraccion;
	}

}

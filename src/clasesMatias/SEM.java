package clasesMatias;

import java.util.ArrayList;
import java.util.List;


import clasesIan.Estacionamiento;
import clasesWalle.AplicacionSEM;

/** Clase que representa el sistema central del SEM, guarda listas de elementos de interes y opera sobre ellas
 * 
 * Tiene la funcionalidad de informar a organismos interesados sobre distintos cambios
 * 
 *  Autor: MazaVega Matias
 * */

public class SEM {

	private SemListener Listener;
	private List<ZonaDeEstacionamiento> zonasEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();
	private List<Compra> compras = new ArrayList<Compra>();
	private List<Estacionamiento> estacionamientosRegistrados = new ArrayList<Estacionamiento>();
	private List<Infraccion> infraccionesRegistradas = new ArrayList<Infraccion>();
	private List<AplicacionSEM> aplicacionesRegistradas = new ArrayList<AplicacionSEM>();
	 /*
	  * Las listas de distintos elementos se crean al inicializarse la clase
	  * */

    //devulve el Listener
	public SemListener getListener() {
		return Listener;
	}

	// establece el Listener
	public void setListener(SemListener listener) {
		Listener = listener;
	}
	
    // agrega una AplicacionSEM a la lista de aplicaciones registradas
	public void registrarAplicacion(AplicacionSEM aplicacion) {
		this.aplicacionesRegistradas.add(aplicacion);
		
	}
	


	//devuelve la lista de aplicaciones registradas
	public List<AplicacionSEM> getAplicacionesRegistradas() {
		return aplicacionesRegistradas;
	}
	
	//agrega una ZonaDeEstacionamiento a la lista de zonas
	public void añadirZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
		this.zonasEstacionamiento.add(zona);
		
	}
	
	//retorna una lista de zonas de estacionamiento
	public List<ZonaDeEstacionamiento> getZonasDeEstacionamiento() {
		
		return this.zonasEstacionamiento;
	}

    //agrega una Compra a la lista de Compras
	public void registrarCompra(Compra compra) {
		this.getCompras().add(compra);
	this.notificarNuevaCompra(compra);	
		
	}
    
	//avisa al Listener que se realizo una nueva compra
	public void notificarNuevaCompra(Compra compra) {
		this.getListener().nuevaCompraRegistrada(this, compra);
		
	}

	//retorna la lista de compras
	public List<Compra> getCompras() {
		
		return this.compras;
	}

	//agrega un Estacionamiento a la lista de estacionamientos
	public void registrarEstacionamiento(Estacionamiento nuevoEstacionamiento) {
		this.getEstacionamientos().add(nuevoEstacionamiento);
		this.notificarNuevoEstacionamiento(nuevoEstacionamiento);
		
	}
    
	//avisa al Listener que se inicio un nuevo estacionamiento
	public void notificarNuevoEstacionamiento(Estacionamiento nuevoEstacionamiento) {
		this.getListener().nuevoEstacionamientoIniciado(this, nuevoEstacionamiento);
		
	}

	//retorna la lista de estacionamientos
	public List<Estacionamiento> getEstacionamientos() {
		
		return this.estacionamientosRegistrados ;
	}

	//le indica a todas las aplicaciones que finalicen sus estacionamientos
	public void finDeFranjaHoraria() {
		this.getAplicacionesRegistradas().stream().forEach(a -> a.finalizarEstacionamiento());
		
		
	}

	//retorna un booleano dependiendo si hay un Estacionamiento vigente con una determinada patente
	public boolean verificarEstacionamientoVigentePorPatente(String patente) {
		return this.estacionamientosRegistrados.stream().anyMatch(a -> a.getPatente().equals(patente));		
	}
	
	//retorna un booleano dependiendo si hay un Estacionamiento vigente con un determinado numero de telefono
	public boolean verificarEstacionamientoVigentePorCelular(int numeroCelular) {
		return this.estacionamientosRegistrados.stream().anyMatch(a -> a.getNumeroDeCelularDeEstacionamiento() == numeroCelular);		
	}

	//agrega una Infraccion a la lista de infracciones
	public void registrarInfraccion(Infraccion infraccion) {
		this.infraccionesRegistradas.add(infraccion);
		
	}

	//retorna la lista de infracciones
	public List<Infraccion> getInfraccionesRegistradas() {
		return infraccionesRegistradas;
	}
	
	//busca en la lista y retorna un Estacionamiento para un numero determinado
	public Estacionamiento buscarPorNumeroCelular(int numeroCelular) {
		Estacionamiento estacionamiento;
		estacionamiento = this.getEstacionamientos().stream()
				.filter(e -> e.getNumeroDeCelularDeEstacionamiento() == numeroCelular).toList().get(0);
	
		return estacionamiento;
		
				
	}



	// le dice que finalice a un determinado Estacionamiento que posea un determinado numero de celular 
	public void finalizarEstacionamientoCon(int numeroDeCelular) {
		Estacionamiento estacionamiento = this.buscarPorNumeroCelular(numeroDeCelular);
		estacionamiento.finalizarEstacionamiento();
		this.notificarFinEstacionamiento(estacionamiento);
		
	}

	public void notificarFinEstacionamiento(Estacionamiento estacionamiento) {
		this.getListener().nuevoFinDeEstacionamiento(this, estacionamiento);
		
	}




}

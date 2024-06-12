package clasesMatias;

import java.util.ArrayList;
import java.util.List;


import clasesIan.Estacionamiento;
import clasesWalle.AplicacionSEM;

public class SEM {

	private List<ZonaDeEstacionamiento> zonasEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();
	private List<Compra> compras = new ArrayList<Compra>();
	private List<Estacionamiento> estacionamientosRegistrados = new ArrayList<Estacionamiento>();
	private List<Infraccion> infraccionesRegistradas = new ArrayList<Infraccion>();
	private List<INotificar> organismosInteresados = new ArrayList<INotificar>();
	private List<AplicacionSEM> aplicacionesRegistradas = new ArrayList<AplicacionSEM>();
	




	public void registrarAplicacion(AplicacionSEM aplicacion) {
		this.aplicacionesRegistradas.add(aplicacion);
		
	}
	
	public List<AplicacionSEM> getAplicacionesRegistradas() {
		return aplicacionesRegistradas;
	}
	
	
	public void añadirZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
		this.zonasEstacionamiento.add(zona);
		
	}
	
	public List<ZonaDeEstacionamiento> getZonasDeEstacionamiento() {
		
		return this.zonasEstacionamiento;
	}


	public void registrarCompra(Compra compra) {
		this.getCompras().add(compra);
		
		
	}

	public List<Compra> getCompras() {
		
		return this.compras;
	}

	
	public void registrarEstacionamiento(Estacionamiento nuevoEstacionamiento) {
		this.getEstacionamientos().add(nuevoEstacionamiento);
		
		
	}

	public List<Estacionamiento> getEstacionamientos() {
		
		return this.estacionamientosRegistrados ;
	}

	public void finDeFranjaHoraria() {
		this.getAplicacionesRegistradas().stream().forEach(a -> a.finalizarEstacionamiento(a.getNumeroDeCelular()));
		
		
	}

	public boolean verificarEstacionamientoVigente(String patente) {
		return this.estacionamientosRegistrados.stream().anyMatch(a -> a.getPatente().equals(patente));		
	}
	
	public boolean verificarEstacionamientoVigente(int numeroCelular) {
		return this.estacionamientosRegistrados.stream().anyMatch(a -> a.getNumeroDeCelularDeEstacionamiento() == numeroCelular);		
	}

	public void registrarInfraccion(Infraccion infraccion) {
		this.infraccionesRegistradas.add(infraccion);
		
	}

	public List<Infraccion> getInfraccionesRegistradas() {
		return infraccionesRegistradas;
	}
	
	public Estacionamiento buscarPorNumeroCelular(int numeroCelular) {
		Estacionamiento estacionamiento;
		estacionamiento = this.getEstacionamientos().stream()
				.filter(e -> e.getNumeroDeCelularDeEstacionamiento() == numeroCelular).toList().get(0);
		//this.notificarOrganismosInteresados();
		return estacionamiento;
		
				
	}

	public void suscribirOrganismosInteresados(INotificar organismoInteresado) {
		this.organismosInteresados.add(organismoInteresado);
		
	}

	public List<INotificar> getOrganismosInteresados() {
		return organismosInteresados;
	}

	public void notificarOrganismosInteresados() {
		this.getOrganismosInteresados().stream().forEach(o -> o.actualizar(this));
		
	}

	public void desuscribirOrganismosInteresados(INotificar organismoInteresado) {
		this.getOrganismosInteresados().remove(organismoInteresado);
		
	}

	public void finalizarEstacionamientoCon(int numeroDeCelular) {
		Estacionamiento estacionamiento = this.buscarPorNumeroCelular(numeroDeCelular);
		estacionamiento.finalizarEstacionamiento();
		
	}




}

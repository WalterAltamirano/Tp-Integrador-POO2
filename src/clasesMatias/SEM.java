package clasesMatias;

import java.util.ArrayList;
import java.util.List;

import clasesIan.Auto;
import clasesIan.Estacionamiento;

public class SEM {

	private List<ZonaDeEstacionamiento> zonasEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();
	private List<Compra> compras = new ArrayList<Compra>();
	private List<Estacionamiento> estacionamientosRegistrados = new ArrayList<Estacionamiento>();
	private List<Infraccion> infraccionesRegistradas = new ArrayList<Infraccion>();

	public void añadirZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
		this.getZonasDeEstacionamiento().add(zona);
		
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
		this.getEstacionamientos().stream().forEach(e -> e.finalizarEstacionamiento());
		
	}

	public boolean verificarEstacionamientoVigente(String patente) {
		return this.estacionamientosRegistrados.stream().anyMatch(a -> a.getPatente().equals(patente));
		
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
			
		return estacionamiento;
		
   
		
	}




}

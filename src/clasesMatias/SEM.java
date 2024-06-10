package clasesMatias;

import java.util.ArrayList;
import java.util.List;

import clasesIan.Estacionamiento;

public class SEM {

	private List<ZonaDeEstacionamiento> zonasEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();
	private List<Compra> compras = new ArrayList<Compra>();
	private List<Estacionamiento> estacionamientosRegistrados = new ArrayList<Estacionamiento>();

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




}

package clasesMatias;

import java.util.ArrayList;
import java.util.List;

public class SEM {

	private List<ZonaDeEstacionamiento> zonasEstacionamiento = new ArrayList<ZonaDeEstacionamiento>();

	public void añadirZonaDeEstacionamiento(ZonaDeEstacionamiento zona) {
		this.getZonasDeEstacionamiento().add(zona);
		
	}
	

	public List<ZonaDeEstacionamiento> getZonasDeEstacionamiento() {
		
		return this.zonasEstacionamiento;
	}

}

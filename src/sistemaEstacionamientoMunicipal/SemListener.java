package sistemaEstacionamientoMunicipal;

import estacionamiento.Estacionamiento;
import puntoDeVentaYCompras.Compra;

/** Interfaz que deberan implementar los distintos organizmos interesados en recibir actualizaciones
 * acerca de los cambios ocurridos en el SEM 
 * 
 *  Autor: MazaVega Matias
 * */
public interface SemListener {

	


	//informa sobre una nueva compra registrada
	public void nuevaCompraRegistrada(SEM sem, Compra compra);

	//informa sobre un nuevo inicio de estacionamiento
	public void nuevoEstacionamientoIniciado(SEM sem, Estacionamiento nuevoEstacionamiento);

	//informa sobre la finalizacion de un estacionamiento
	public void nuevoFinDeEstacionamiento(SEM sem, Estacionamiento estacionamiento);

}

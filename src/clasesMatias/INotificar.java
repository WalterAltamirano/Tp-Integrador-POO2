package clasesMatias;


/** Interfaz que deberan implementar los distintos organizmos interesados en recibir actualizaciones
 * acerca de los cambios ocurridos en el SEM 
 * 
 *  Autor: MazaVega Matias
 * */
public interface INotificar {

	
	//recibe el SEM que se paso a si mismo para que cada organismo luego pregunte lo que le es de interes
	Object actualizar(SEM sem);

}

package puntoDeVentaYCompras;


import aplicacionSEM.AplicacionSEM;
import estacionamiento.Estacionamiento;
import estacionamiento.EstacionamientoCompraPuntual;
import sistemaEstacionamientoMunicipal.SEM;

/** Clase que representa el PuntoDeVenta en que se puede cargarSaldo para las Aplicaciones SEM
 * 
 * El SEM se asigna de forma manual ya que se considero que un PuestoDeVenta no deberia necesitar un SEM para existir
 * 
 * Se determino que no es posible comprar mas horas de Estacionamiento que lo que permitiria la hora limite
 * 
 *  Autor: MazaVega Matias
 * */

public class PuntoDeVenta {
	
	private SEM sem;
    /*Se inicializa sin recibir ningun parametro y se le asigna manualmente el SEM
     * */
	
	//devuelve el un SEM
	public SEM getSem() {
		return sem;
	}

	//asigna el SEM al PuestoDeVenta
	public void setSem(SEM sem) {
		this.sem = sem;
	}

	
	// carga el saldo indicado a la aplicacion que se pasa por parametro
	// genera una Compra de CargaDeSaldo y la pide al SEM que la cargue en su lista de Compras
	
	public void cargarSaldo(AplicacionSEM aplicacion, double saldo) {
		aplicacion.cargarSaldo(saldo);
		CargaDeSaldo nuevaCarga = this.generarCompraSaldo(saldo, aplicacion);
		this.getSem().registrarCompra(nuevaCarga);
		
		
	}
	
	// crea una instacia de CargaDeSaldo y la retorna
	public CargaDeSaldo generarCompraSaldo(double saldo, AplicacionSEM aplicacion) {
		CargaDeSaldo nuevaCarga = new CargaDeSaldo(this, saldo, aplicacion);
		return nuevaCarga;
	}
    

	// genera un EstacionamientoCompraPuntual y la CompraPuntual correspondiente 

	public void registrarEstacionamiento(String patente, int cantidadDeHoras ) {
		
		CompraPuntual nuevaCompraEstacionamiento = this.generarNuevaCompra(cantidadDeHoras);
	    this.generarNuevoEstacionamiento(nuevaCompraEstacionamiento, patente);
		
		
	}
	
	// Genera una nueva CompraPuntual, la retorna y le pide al SEM que la agregue a su lista
	public CompraPuntual generarNuevaCompra(int cantidadDeHoras) {
		CompraPuntual nuevaCompraEstacionamiento = new CompraPuntual(this, cantidadDeHoras);
		this.getSem().registrarCompra(nuevaCompraEstacionamiento);
		return nuevaCompraEstacionamiento;
	}
	
	// Genera un nuevo EstacionamientoCompraPuntual, lo retorna y le pide al SEM que la agregue a su lista
	public EstacionamientoCompraPuntual generarNuevoEstacionamiento(CompraPuntual nuevaCompraEstacionamiento, String patente) {
		EstacionamientoCompraPuntual nuevoEstacionamiento = new EstacionamientoCompraPuntual(nuevaCompraEstacionamiento, patente);
		this.getSem().registrarEstacionamiento(nuevoEstacionamiento);
		return nuevoEstacionamiento;
	}
	

}

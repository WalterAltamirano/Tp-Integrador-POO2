package clasesWalle;

public class AplicacionSEM implements MovementSensor {
	
	private boolean usuarioEstaCaminando;
    //private int horaActual;
	private double saldoAcreditado;
	private int numeroDeCelular;
	//private SEM sistemaEstacionamiento;
	private Modo modo;
	private EstadoGPS gps;
	private DeteccionDesplazamiento deteccionDeDesplazamiento;

	public AplicacionSEM(Modo modo) {
		super();
		this.saldoAcreditado = 0;
		this.numeroDeCelular = 0;
		this.modo = modo;
	}
	
	public void inicioEstacionamiento(int numeroCelular,String patente) {}
	
	public void finalizarEstacionamiento(int numeroCelular) {}
	
	public void elegirModo(Modo modo) {}
	
	public void encenderGPS() {}
	
	public void apagarGPS() {}
	
	public void cargarSaldo(double saldoACargar) {}

	@Override
	public void driving() {
		this.usuarioEstaCaminando = false;
		deteccionDeDesplazamiento.notificarAlerta(this);
		
	}
	@Override
	public void walking() {
		this.usuarioEstaCaminando = true;
		deteccionDeDesplazamiento.notificarAlerta(this);
	}

	void setEstaCaminando(boolean valor) {
		this.usuarioEstaCaminando = valor;
	}
	
	boolean getEstaCaminando() {
		return this.usuarioEstaCaminando;
	}
	
	
  //public double consultarSaldo() {} //Pide getCredito()
	
	
//	private double getCredito() {   //Primero hay que testear
//		return this.saldoAcreditado;
//	}
	
	//Metodos privados
//	private int getNumeroDeCelular() {
//		return this.numeroDeCelular;
//	}
//  private void setModoApp(Modo modo) {
//		this.modo = modo;
//  }
//  private boolean hayEstacionamientoCon(String patente) {
//      return sistemaEstacionamiento.verificarSiEstaEstacionado(patente);
//	}
//	
//   private int calcularCreditoAPagar() {
//	   int cantidadDeHoras = 20 - this.horaActual;
//	   return 40 * cantidadDeHoras;
//   }
}


/*

  //Para verificar el credito disponible
   * if(this.calcularCreditoAPagar() <= this.saldoAcreditado) {
		   return 
      }
  
  
  
  Evento 1 via app
   *Para dar una respuesta, deberia hacer un "system.out.print("mensaje");"
   
   Otra opcion
    *
   
  
  / Interfaz MovementSensor
   * En el test, deberia en algun momento del execercise
   * enviar el mensaje driving o walking dependiendo en que escenario
   * de testeo me encuentre
  
  
  / Se envia el mensaje inicioEstacionamiento()
     * verifica que (Constantemente recibiendo uno de los dos mensajes "driving()" o "walking()") 
       1. No haya un estacionamiento vigente con esa patente
       2. Que el mensaje que reciba sea "walking()"
           *(Es decir, que le envia a su estado el mensaje getEstaCaminando()), 
       3. Que tenga credito.
       4. Que se encuentre en una zona de estacionamiento
      
      Entonces toma las siguientes decisiones: 
        1. deteccionDeDesplazamiento.notificarAlerta()  --Independientemente de que si esta activada o no
        2. sistemaEstacionamiento.registrarEstacionamiento(new Estaciomiento(patente,nroCelular,this.horaActual))
        3. 
        4. 
      
    ** el saldo a debitar se calcula una vez que termine el estacionamiento 
       ya que todavia no sabria cuantas horas va a dejar estacionado el auto añaña**
       *this.saldoAcreditado = this.saldoAcreditado - this.calcularCostoEstacionamiento()*
    
    
    /Se envia el mensaje finalizarEstacionamiento()
     *
      
    
    
    
    
    
    
  
 * */

package clasesWalle;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import clasesIan.EstadoApp;
import clasesIan.Usuario;
import clasesMatias.SEM;
import clasesIan.EnAuto;
import clasesIan.Caminando;

public class AplicacionSEM implements MovementSensor {
	
	//Variables de Instancia
    private LocalDateTime horaInicio;
    private LocalDateTime horaFin;
	private double saldoAcreditado;
	private int numeroDeCelular;
	private SEM sistemaEstacionamiento;
	private Modo modo;
	private EstadoGPS gps;
    private EstadoApp estado;
    private Usuario usuario;
    
    //Constructor
	public AplicacionSEM(Modo modo, EstadoApp estado, SEM sistemaDeEstacionamiento, Usuario usuario,int nroDeCelular,LocalDateTime horaDeInicio, LocalDateTime horaFin) {
		super();
		this.saldoAcreditado = 0;
		this.numeroDeCelular = nroDeCelular;
		this.modo = modo;
		this.estado = estado;
		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
		this.usuario = usuario;
		this.horaInicio = horaDeInicio;
		this.horaFin = horaFin;
	}
	
	
					    //Mensajes Publicos
//	!-------------------------------------------------------------------------!
	public void inicioEstacionamiento(int numeroCelular,String patente) {
		this.getModo().inicioDeEstacionamiento(this, numeroCelular, patente);
	}
	
	public void finalizarEstacionamiento(int numeroCelular) {
		this.getModo().finDeEstacionamiento(this, numeroCelular);
	}
	
	public void elegirModo(Modo modo) {
		this.setModoApp(modo);
		this.getModo().avisoDeCambio();
	}
	
	public void elegirEstadoGPS(EstadoGPS estado) {
		this.setEstadoGPS(estado);
	}
	
	public void cargarSaldo(double saldoACargar) {
		this.saldoAcreditado = this.saldoAcreditado + saldoACargar;
	}
	
	public double consultarSaldo() {
		return this.getCredito();
	}
	public void asignarCelular() {
		
	}
	
	public int getNumeroDeCelular() { 
	   return this.numeroDeCelular;
	}
							//Metodos de interfaz
//	!-------------------------------------------------------------------------!
	@Override
	public void driving() {
		estado.manejando(this);
		
	}
	@Override
	public void walking() {
		estado.caminando(this);
	}
//	!-------------------------------------------------------------------------!	

	
						//Metodos de visibilidad de Paquete
//	!-------------------------------------------------------------------------!	
	void setEstado(EstadoApp estado) {
		this.estado = estado;
	}
	void alertaInicioDeEstacionamiento() {
		this.getModo().notificarAlertaDeInicioDeEstacionamiento(this);
	}
	void alertaFinDeEstacionamiento() {
		this.getModo().notificarAlertaDeFinDeEstacionamiento(this);
	}
	EstadoGPS getGps() {
		return this.gps;
	}
	boolean hayEstacionamientoCon(String patente) {
	    return sistemaEstacionamiento.verificarEstacionamientoVigente(patente);
	}
	boolean hayEstacionamientoCon(int nroDeCelular) {
	    return sistemaEstacionamiento.verificarEstacionamientoVigente(nroDeCelular);
	}
	double getCredito() {   
	    return this.saldoAcreditado;
	}
	boolean tieneCreditoSuficienteParaEstacionar() {
		return this.saldoAcreditado > this.valorPorHoraDeEstacionamiento();
	}
	boolean estaEnZonaDeEstacionamiento() { 
	    return this.getGps().estaEncendido();
    }
	SEM getSistemaEstacionamiento() {
		return this.sistemaEstacionamiento;
	}
	Usuario getUsuario() {
		return this.usuario;
	}
	Modo getModo() {
		return this.modo;
	}
	void descontarSaldo() {
		this.saldoAcreditado = this.saldoAcreditado - this.calcularCreditoAPagar();
	}
	int getHoraInicio() {
		return this.horaInicio.getHour();
	}
	int horaFinal() { //Para el modo automatico
		if(this.puedePagarHastaFinDeFranjaHoraria()) {
			return this.horaMaximaDeEstacionamiento();
		}
		else {
			return this.getHoraInicio() + this.cantidadDeHorasSegunSaldo();
		}
		
	}
	int calcularHoraFin() { //Para el modo manual
		return this.getHoraInicio() + this.horaFin.getHour();
	}
	int minutoFin() {
		return LocalDateTime.now().getMinute();
	}
	int minutoInicio() {
		return this.horaInicio.getMinute();
	}
//	!-------------------------------------------------------------------------!
	
	
							  //Metodos privados
//	!-------------------------------------------------------------------------!	
	
    private void setModoApp(Modo modo) {
		this.modo = modo;
   }
   private int valorPorHoraDeEstacionamiento() {
	   return 40;
   }
   
   private int horaMaximaDeEstacionamiento() {
	   return 20;
   }
   private int cantidadDeHorasSegunSaldo() {
	   return (int) this.saldoAcreditado / this.valorPorHoraDeEstacionamiento();
   }
   private int cantidadDeHorasMaximas() {
	   return this.horaMaximaDeEstacionamiento() - this.getHoraInicio();
   }
   private boolean puedePagarHastaFinDeFranjaHoraria() {
       return this.cantidadDeHorasSegunSaldo() >= this.cantidadDeHorasMaximas();
    	
    }
   private double calcularCreditoAPagar() {
    	if(this.puedePagarHastaFinDeFranjaHoraria()) {
    		return this.valorPorHoraDeEstacionamiento() * this.cantidadDeHorasMaximas();
    	} 
    	else {
    		return this.cantidadDeHorasSegunSaldo() * this.valorPorHoraDeEstacionamiento();
    	}
    }
	
   private void setEstadoGPS(EstadoGPS estado) {
    	this.gps = estado;
    }
//	!-------------------------------------------------------------------------!


	


	
    
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
  
  
  / Se envia el mensaje inicioEstacionamiento() (Modo manual)
     * verifica que (Constantemente recibiendo uno de los dos mensajes "driving()" o "walking()") 
       1. No haya un estacionamiento vigente con esa patente
       2. Que el estadoApp va a ser Caminando (El estado se encarga de enviar el mensaje "alerta")
         a.esta en una zona de estacionameinto
         b.se detecta que el desplazamiento es de manejando a caminando
       3. Que tenga credito.
       4. Que se encuentre en una zona de estacionamiento
      
      Entonces toma las siguientes decisiones: 
        1. modo.notificarAlertaInicio()  --Independientemente de que si esta activada o no
        2. sistemaEstacionamiento.registrarEstacionamiento(new Estaciomiento(patente,nroCelular,this.horaActual))
        3. 
        4. 
      
    ** el saldo a debitar se calcula una vez que termine el estacionamiento 
       ya que todavia no sabria cuantas horas va a dejar estacionado el auto añaña**
       *this.saldoAcreditado = this.saldoAcreditado - this.calcularCostoEstacionamiento()*
    
    
    /Se envia el mensaje finalizarEstacionamiento()
     *
      
    
    
    
    
    
    private boolean estaEnZonaDeEstacionamiento() { //!Una idea!
//		List<ZonaEstacionamiento> zonas = sistemaEstacionamiento.getZonasDeEstacionamiento();
//		List<ZonaEstacionamieto> zonaQueCoincide = zonas.stream().
//								filter( zona -> zona.puntoGeografico() == gps.puntoGeograficoActual())
//								.Collect(Collectors.toList());
//		return !zonaQueCoincide.isEmpty();
  
 * */

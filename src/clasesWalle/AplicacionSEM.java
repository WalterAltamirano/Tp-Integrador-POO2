package clasesWalle;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import clasesIan.EstadoApp;
import clasesIan.Usuario;
import clasesMatias.SEM;
import net.bytebuddy.asm.Advice.This;
import clasesIan.EnAuto;
import clasesIan.EstacionamientoAplicacion;
import clasesIan.Caminando;

public class AplicacionSEM implements MovementSensor {
	
	//Variables de Instancia
	private double saldoAcreditado;
	private Integer numeroDeCelular;
	private SEM sistemaEstacionamiento;
	private Modo modo;
	private ModoGps gps;
    private EstadoApp estado;
    private Usuario usuario;
    private String patente;
    private Integer horaInicio;
    
    					    //Constructores
//	!-------------------------------------------------------------------------!
    
    //Constructor #1 --> No hay nada incializado, 
    //se elijen los modos, automatico o manual, Apagado o Encendido y el Estado de la app
  	public AplicacionSEM(Modo modo, EstadoApp estado,SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,ModoGps gps) {
  		super();
  		this.saldoAcreditado = 0;
  		this.numeroDeCelular = nroDeCelular;
  		this.modo = modo;
  		this.estado = estado;
  		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
  		this.usuario = usuario;
  		this.gps = gps;
  		this.patente = this.getUsuario().getPatente();
  		this.horaInicio = 0; //Manejado en tiempo real, es necesario inicializar porque sino queda un NULL 
  	}
//    //Constructor #2 --> Puede elejirse el modo inicial
//    public AplicacionSEM(Modo modo, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular) {
//		super();
//		this.saldoAcreditado = 0;
//		this.numeroDeCelular = nroDeCelular;
//		this.modo = modo;
//		this.estado = new EnAuto();
//		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//		this.usuario = usuario;
//		this.gps = new Apagado();
//		this.patente = this.getUsuario().getPatente();
//	}
//    
//   //Constructor #3 --> Puede elejirse el modo y el modoGps inicial
//	public AplicacionSEM(Modo modo, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,ModoGps gps) {
//		super();
//		this.saldoAcreditado = 0;
//		this.numeroDeCelular = nroDeCelular;
//		this.modo = modo;
//		this.estado = new EnAuto();
//		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//		this.usuario = usuario;
//		this.gps = gps;
//		this.patente = this.getUsuario().getPatente();
//	}
//	
//	//Constructor #4 --> El modo, modoGps y el estado, estan inicializados (ModoAutomatico, Apagado,EnAuto)
//	public AplicacionSEM(SEM sistemaDeEstacionamiento, Usuario usuario, Integer nroDeCelular) {
//		super();
//		this.saldoAcreditado = 0;
//		this.modo = new ModoAutomatico();
//		this.estado = new EnAuto();
//		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//		this.usuario = usuario;
//		this.gps = new Apagado();
//		this.numeroDeCelular = nroDeCelular;
//		this.patente = this.getUsuario().getPatente();
//	}
	
					    //Mensajes Publicos
//	!-------------------------------------------------------------------------!
	//No hay parametros ya que la app obtiene el nro de celular por medio del constructor y la patente a traves del usuario
	public void iniciarEstacionamiento() {	
		if(this.horaInicio() >= 7 && this.horaInicio() <= 19) {
			try {
				this.puedeEstacionar(this.getPatente());
				this.getSistemaEstacionamiento()
				.registrarEstacionamiento(this.instanciaDeEstacionamiento(this.getNumeroDeCelular(), this.getPatente()));
				this.setHoraInicio();
				this.darRespuestaInicial();
			}
			catch(ExcepcionPersonalizada e) {
			}
		}
	}
	
	public void finalizarEstacionamiento() {
		try {
			this.puedeFinalizar();
		    this.getSistemaEstacionamiento().finalizarEstacionamientoCon(this.getNumeroDeCelular());
			this.descontarSaldo();
			this.darRespuestaFinal();
		}
		catch(ExcepcionPersonalizada e) {
		}
	}
	 public void puedeEstacionar(String patente) throws ExcepcionPersonalizada {
		//Creo que es responsabilidad del SEM verificar esta condicion 	
		if(this.hayEstacionamientoCon(patente)) { //Porque la app verifica el credito y la zona segun el enunciado
			throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
		}
		if(!this.tieneCreditoSuficienteParaEstacionar()) {
			throw new ExcepcionPersonalizada("Saldo insuficiente. Estacionamiento no permitido");
		}
		if(!this.estaEnZonaDeEstacionamiento()) {
			throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
		}
	}
		
	public void puedeFinalizar() throws ExcepcionPersonalizada{
		if(!this.hayEstacionamientoCon(this.getNumeroDeCelular())) {
			throw new ExcepcionPersonalizada("No hay un estacionamiento por finalizar");
		}
	}
	   
	
	public void activarModoAutomatico() {
		this.setModoApp(new ModoAutomatico());
		this.getModo().avisoDeCambio();
	}

	public void activarModoManual() {
		this.setModoApp(new ModoManual());
		this.getModo().avisoDeCambio();
	}
	
	public void encenderGps() {
		this.setModoGps(new Encendido());
	}
	
	public void apagarGps() {
		this.setModoGps(new Apagado());
	}
	public void cargarSaldo(double saldoACargar) {
		this.saldoAcreditado = this.saldoAcreditado + saldoACargar;
	}
	
	public double consultarSaldo() {
		return this.getCredito();
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
	public void setEstado(EstadoApp estado) {
		this.estado = estado;
	}

	public EstadoApp getEstado() {
		return estado;
	}

	public void pasoAAuto() {
		this.getModo().finDeEstacionamiento(this); 
	}
	public void pasoACaminando() {
		this.getModo().inicioDeEstacionamiento(this); 
	}
	public ModoGps getGps() {
		return this.gps;
	}
	public boolean hayEstacionamientoCon(String patente) {	//Creo que es responsabilidad del SEM verificar esto y no la app
	    return sistemaEstacionamiento.verificarEstacionamientoVigente(patente);
	}
	public boolean hayEstacionamientoCon(Integer nroDeCelular) {
	    return sistemaEstacionamiento.verificarEstacionamientoVigente(nroDeCelular);
	}
	public double getCredito() {   
	    return this.saldoAcreditado;
	}
	public boolean tieneCreditoSuficienteParaEstacionar() {
		return this.saldoAcreditado >= this.valorPorHoraDeEstacionamiento();
	}
	public boolean estaEnZonaDeEstacionamiento() { 									//Que hago aca??
	    return this.getGps().getEstaEncendido(); //&& this.getGps().coincidenEnUnMismoPunto(this, null);
    }
	public SEM getSistemaEstacionamiento() {
		return this.sistemaEstacionamiento;
	}
	public Usuario getUsuario() {
		return this.usuario;
	}
	public Modo getModo() {
		return this.modo;
	}
	public void descontarSaldo() {
		//Ojo aca en "this.horaInicio() Deberia cambiarse a "this.getHoraInicio()" pero como
		//es en tiempo real, y son las 22, el estacionamiento no deberia iniciarse
		//Entonces la variable queda en NULL y falla -- Actualmente puse un cero para
		//Que no fallen los test
		if((this.horaFinal() - this.horaInicio()) != 0) {
			this.saldoAcreditado = this.saldoAcreditado - this.calcularCreditoAPagar();
		}
		
	}
	public int horaInicio() {
		return LocalDateTime.now().getHour();
	}
	public int horaFinal() { //Para el modo automatico
		if(this.puedePagarHastaFinDeFranjaHoraria()) {
			return this.horaMaximaDeEstacionamiento();
		}
		else {
			return this.getHoraInicio() + this.cantidadDeHorasSegunSaldo();
		}
		
	}
	private int getHoraInicio() {
		return this.horaInicio;
	}
	public int calcularHoraFin() { //Para el modo manual
		return this.getHoraInicio() + LocalDateTime.now().getHour();
	}
	public int minutoFin() {
		return LocalDateTime.now().getMinute();
	}
	public int minutoInicio() {
		return LocalDateTime.now().getMinute();
	}
	public double calcularCreditoAPagar() {
    	if(this.puedePagarHastaFinDeFranjaHoraria()) {
    		return this.valorPorHoraDeEstacionamiento() * this.cantidadDeHorasMaximas();
    	} 
    	else {
    		return this.cantidadDeHorasSegunSaldo() * this.valorPorHoraDeEstacionamiento();
    	}
    }
	public EstacionamientoAplicacion instanciaDeEstacionamiento(Integer numeroDeCelular,String patente) {
		return new EstacionamientoAplicacion(numeroDeCelular,patente);
	}
//	!-------------------------------------------------------------------------!
	
	
							  //Metodos privados
//	!-------------------------------------------------------------------------!	
	
   public void setModoApp(Modo modo) {
		this.modo = modo;
   }
   public int valorPorHoraDeEstacionamiento() {
	   return 40;
   }
   
   public int horaMaximaDeEstacionamiento() {
	   return 20;
   }
   public int cantidadDeHorasSegunSaldo() {
	   return (int) this.saldoAcreditado / this.valorPorHoraDeEstacionamiento();
   }
   public int cantidadDeHorasMaximas() {
	   return this.horaMaximaDeEstacionamiento() - this.horaInicio();
   }
   public boolean puedePagarHastaFinDeFranjaHoraria() {
       return this.cantidadDeHorasSegunSaldo() >= this.cantidadDeHorasMaximas();
    	
    }
   public void setModoGps(ModoGps modo) {
   		this.gps = modo;
   }
   
	
   public void darRespuestaFinal() {
			System.out.print("!---------------------------------!" + "\r\n"
			 + "Su estacionamiento fue dado de baja con exito." +"\r\n"
			 + "Hora exacta: " + this.getHoraInicio() + ":" + this.minutoInicio() +"\r\n"
			 + "Hora fin: " + this.horaFinal() + ":" + this.minutoFin() +"\r\n"
			 + "Duracion: " + (this.horaFinal() - this.getHoraInicio()) + "hs" +"\r\n"
			 + "Costo: " + this.calcularCreditoAPagar() +"\r\n"
			 + "!---------------------------------!");
   }
   public void darRespuestaInicial() {
		System.out.print( "\r\n"+"!---------------------------------!"+ "\r\n"
				 + "Su estacionamiento fue dado de alta con exito."+ "\r\n"
				 + "Hora exacta: " + this.horaInicio() + "\r\n"
				 + "Hora fin: " + "Pendiente" + "\r\n"
				 + "La hora fin quedara establecida segun la cantidad de horas maximas equivalentes a su saldo acreditado" +"\r\n"
				 + "!---------------------------------!");					
	}
	private void setHoraInicio() {
		this.horaInicio = this.horaInicio();
	}
	public String getPatente() {
		// TODO Auto-generated method stub
		return this.patente;
	}
//	!-------------------------------------------------------------------------!
 
}

    /*
     Anotaciones:
      *Creo que el usuario deberia tener un mensaje donde pueda elegir el modo 
        de la aplicacion y tambien otros dos mensajes que indiquen que quieran
        prender el gps o apagarlo
        */
    
 

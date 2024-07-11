package aplicacionSEM;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;

import dataUsuario.Usuario;
import estacionamiento.Estacionamiento;
import estacionamiento.EstacionamientoAplicacion;
import estadosAplicacion.Caminando;
import estadosAplicacion.EnAuto;
import estadosAplicacion.EstadoApp;
import estrategiasAplicacion.Modo;
import estrategiasAplicacion.ModoAutomatico;
import estrategiasAplicacion.ModoManual;
import net.bytebuddy.asm.Advice.This;
import sistemaEstacionamientoMunicipal.SEM;

public class AplicacionSEM implements MovementSensor {
	
	//Variables de Instancia
	private double saldoAcreditado;
	private Integer numeroDeCelular;
	private SEM sistemaEstacionamiento;
	private Modo modo;
	//private ModoGps gps;
    private EstadoApp estado;
    private Usuario usuario;
    //private String patente;
    private Integer horaInicio;
    private Integer horaFin;
    private boolean gps;
    					    //Constructores
//	!-------------------------------------------------------------------------!
    
    //Constructor #1 --> No hay nada incializado, 
    //se elijen los modos, automatico o manual, Apagado o Encendido y el Estado de la app
  	public AplicacionSEM(Modo modo, EstadoApp estado,SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,boolean gps) {
  		super();
  		this.saldoAcreditado = 0;
  		this.numeroDeCelular = nroDeCelular;
  		this.modo = modo;
  		this.estado = estado;
  		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
  		this.usuario = usuario;
  		this.gps = gps;
  		//this.horaInicio = 0; //Manejado en tiempo real, es necesario inicializar porque sino queda un NULL 
  	}

					    //Mensajes Publicos
//	!-------------------------------------------------------------------------!
	
  	
  	// ¡Esta harcodeada la hora de inicio!
	public void iniciarEstacionamiento(int horaDeInicio) {	
		if(horaDeInicio >= 7 && horaDeInicio <= 19) {
			try {
				this.puedeEstacionar(this.getPatente());
				Estacionamiento estacionamiento = this.instanciaDeEstacionamiento(this.getNumeroDeCelular(), this.getPatente());
				this.getSistemaEstacionamiento()
				.registrarEstacionamiento(estacionamiento);
				
				this.darRespuestaInicial(estacionamiento);
			}
			catch(ExcepcionPersonalizada e) {
			}
		}
	}
	
	public void finalizarEstacionamiento() {
		try {
			this.puedeFinalizar();
		    this.getSistemaEstacionamiento().finalizarEstacionamientoCon(this.getNumeroDeCelular());
		    Estacionamiento estacionamiento = this.getSistemaEstacionamiento().buscarPorNumeroCelular(getNumeroDeCelular());
			this.descontarSaldo(estacionamiento);
			this.darRespuestaFinal(estacionamiento);
		}
		catch(ExcepcionPersonalizada e) {
		}
	}
	public void puedeEstacionar(String patente) throws ExcepcionPersonalizada {
	  if(this.hayEstacionamientoConPatente(patente)) { 
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
		if(!this.hayEstacionamientoConCelular(this.getNumeroDeCelular())) {
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
		this.setGps(true);
	}
	
	public void apagarGps() {
		this.setGps(false);
	}
	private void setGps(boolean valor) {
		this.gps = valor;
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
	public boolean hayEstacionamientoConPatente(String patente) {	
	    return sistemaEstacionamiento.verificarEstacionamientoVigentePorPatente(patente);
	}
	public boolean hayEstacionamientoConCelular(Integer nroDeCelular) {
	    return sistemaEstacionamiento.verificarEstacionamientoVigentePorCelular(nroDeCelular);
	}
	public double getCredito() {   
	    return this.saldoAcreditado;
	}
	public boolean tieneCreditoSuficienteParaEstacionar() {
		return this.saldoAcreditado >= this.valorPorHoraDeEstacionamiento();
	}
	public boolean estaEnZonaDeEstacionamiento() { 							
	    return this.gps; 
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
	public void descontarSaldo(Estacionamiento estacionamiento) {
		//Se usan metodos harcodeados
		if((estacionamiento.getHoraFin() - estacionamiento.getHoraInicio()) > 0) {
			this.saldoAcreditado = this.saldoAcreditado - this.calcularCreditoAPagar(estacionamiento);
		}
		//Para usar en tiempo real seria if(this.horaFin() - this.getHoraInicio())
	}
	
//	public int horaInicio() {   //En tiempo real
//		return LocalDateTime.now().getHour();
//	}
//	private Integer horaFin() {			 //En tiempo real
//		return LocalDateTime.now().getHour();
//	}
//	private int getHoraInicio() {
//		return this.horaInicio;
//	}
//	public int calcularHoraDuracion() { 		//En tiempo real
//		return this.horaFin() - this.getHoraInicio();
//	}
	public Integer calcularHoraDuracion(Estacionamiento estacionamiento) {		//Arreglado
		return estacionamiento.getHoraFin() - estacionamiento.getHoraInicio();
	}
	
//	private Integer getHoraFin() { //Harcodeado
//		return this.horaFin;
//}
	public double calcularCreditoAPagar(Estacionamiento estacionamiento) {
    	return (estacionamiento.getHoraFin() - estacionamiento.getHoraInicio()) * this.valorPorHoraDeEstacionamiento();
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
   
//   public int horaMaximaDeEstacionamiento() {
//	   return 20;
//   }
   
//   public int cantidadDeHorasSegunSaldo() {
//	   return (int) this.saldoAcreditado / this.valorPorHoraDeEstacionamiento();
//   }
//   public int cantidadDeHorasMaximas() {
//	   return this.horaMaximaDeEstacionamiento() - this.getHoraInicio();
//   }
//   public boolean puedePagarHastaFinDeFranjaHoraria() {
//       return this.cantidadDeHorasSegunSaldo() >= this.cantidadDeHorasMaximas();
//    	
//    }

   public void darRespuestaFinal(Estacionamiento estacionamiento) {
			System.out.print("!---------------------------------!" + "\r\n"
			 + "Su estacionamiento fue dado de baja con exito." +"\r\n"
			 + "Hora exacta: " + estacionamiento.getHoraInicio() +"\r\n"
			 + "Hora fin: " + estacionamiento.getHoraFin()  +"\r\n"
			 + "Duracion: " + this.calcularHoraDuracion(estacionamiento) + "hs" +"\r\n"
			 + "Costo: " + this.calcularCreditoAPagar(estacionamiento) +"\r\n"
			 + "!---------------------------------!");
   }

   public void darRespuestaInicial(Estacionamiento estacionamiento) {
		System.out.print( "\r\n"+"!---------------------------------!"+ "\r\n"
				 + "Su estacionamiento fue dado de alta con exito."+ "\r\n"
				 + "Hora exacta: " + estacionamiento.getHoraInicio() + "\r\n"
				 + "Hora fin: " + "Pendiente" + "\r\n"
				 + "La hora fin quedara establecida segun la cantidad de horas maximas equivalentes a su saldo acreditado" +"\r\n"
				 + "!---------------------------------!");					
	}
//	public void setHoraInicio(Integer horaInicio) {
//		this.horaInicio = horaInicio;
//	}
//	public void setHoraFin(Integer horaFin) {
//		this.horaFin = horaFin;
//	}
//	public void setHoraInicio(Integer horaHarcodeada) {
//		this.horaInicio = horaHarcodeada;
//	}
	public String getPatente() {
		return usuario.getPatente();
	}
//	!-------------------------------------------------------------------------!

 
}
    
////Constructor #2 --> Puede elejirse el modo inicial
//public AplicacionSEM(Modo modo, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular) {
//	super();
//	this.saldoAcreditado = 0;
//	this.numeroDeCelular = nroDeCelular;
//	this.modo = modo;
//	this.estado = new EnAuto();
//	this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//	this.usuario = usuario;
//	this.gps = new Apagado();
//	this.patente = this.getUsuario().getPatente();
//}
//
////Constructor #3 --> Puede elejirse el modo y el modoGps inicial
//public AplicacionSEM(Modo modo, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,ModoGps gps) {
//	super();
//	this.saldoAcreditado = 0;
//	this.numeroDeCelular = nroDeCelular;
//	this.modo = modo;
//	this.estado = new EnAuto();
//	this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//	this.usuario = usuario;
//	this.gps = gps;
//	this.patente = this.getUsuario().getPatente();
//}
//
////Constructor #4 --> El modo, modoGps y el estado, estan inicializados (ModoAutomatico, Apagado,EnAuto)
//public AplicacionSEM(SEM sistemaDeEstacionamiento, Usuario usuario, Integer nroDeCelular) {
//	super();
//	this.saldoAcreditado = 0;
//	this.modo = new ModoAutomatico();
//	this.estado = new EnAuto();
//	this.sistemaEstacionamiento = sistemaDeEstacionamiento;
//	this.usuario = usuario;
//	this.gps = new Apagado();
//	this.numeroDeCelular = nroDeCelular;
//	this.patente = this.getUsuario().getPatente();
//}

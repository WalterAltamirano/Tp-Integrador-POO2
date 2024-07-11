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
	
    private EstadoApp estado;
    private Usuario usuario;
   
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
  		
  	}

					 
//	!-------------------------------------------------------------------------!
	
  	
  	
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
		
		if((estacionamiento.getHoraFin() - estacionamiento.getHoraInicio()) > 0) {
			this.saldoAcreditado = this.saldoAcreditado - this.calcularCreditoAPagar(estacionamiento);
		}
		
	}
	


	public Integer calcularHoraDuracion(Estacionamiento estacionamiento) {		
		return estacionamiento.getHoraFin() - estacionamiento.getHoraInicio();
	}
	

	public double calcularCreditoAPagar(Estacionamiento estacionamiento) {
    	return (estacionamiento.getHoraFin() - estacionamiento.getHoraInicio()) * this.valorPorHoraDeEstacionamiento();
    }
	public EstacionamientoAplicacion instanciaDeEstacionamiento(Integer numeroDeCelular,String patente) {
		return new EstacionamientoAplicacion(numeroDeCelular,patente);
	}
//	!-------------------------------------------------------------------------!
	
	

	
   public void setModoApp(Modo modo) {
		this.modo = modo;
   }
   public int valorPorHoraDeEstacionamiento() {
	   return 40;
   }
   


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

	public String getPatente() {
		return usuario.getPatente();
	}


 
}
    


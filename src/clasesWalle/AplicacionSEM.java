package clasesWalle;

import java.util.List;
import java.util.stream.Collectors;
import java.time.*;
import clasesIan.EstadoApp;
import clasesIan.Usuario;
import clasesMatias.SEM;
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
    
    //Constructor
	public AplicacionSEM(Modo modo, EstadoApp estado, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,ModoGps gps) {
		super();
		this.saldoAcreditado = 0;
		this.numeroDeCelular = nroDeCelular;
		this.modo = modo;
		this.estado = estado;
		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
		this.usuario = usuario;
		this.gps = gps;
		this.patente = this.getUsuario().getPatente();
	}
	
	//De entrada no tendria el celular, sino que se establece una vez iniciado el estacionamiento
	public AplicacionSEM(Modo modo, EstadoApp estado, SEM sistemaDeEstacionamiento, Usuario usuario,ModoGps gps) {
		super();
		this.saldoAcreditado = 0;
		this.modo = modo;
		this.estado = estado;
		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
		this.usuario = usuario;
		this.gps = gps;
	}
	
					    //Mensajes Publicos
//	!-------------------------------------------------------------------------!
	//No hay parametros ya que la app obtiene el nro de celular por medio del constructor y la patente a traves del usuario
	public void iniciarEstacionamiento()  {	
		try {	
			this.puedeEstacionar(this.getPatente());
		}
		catch(ExcepcionPersonalizada e) {
			e.printStackTrace();
		}
		this.getSistemaEstacionamiento()
		.registrarEstacionamiento(this.instanciaDeEstacionamiento(this.getNumeroDeCelular(), this.getPatente()));
		this.darRespuestaInicial();
	}
	

	//Usaria el numero que fue asignado? o el enunciado dice que se envia por parametro?
	public void finalizarEstacionamiento() {
		if(!this.hayEstacionamientoCon(this.getNumeroDeCelular())) {
			 this.getSistemaEstacionamiento().finalizarEstacionamientoCon(this.getNumeroDeCelular());
			 this.descontarSaldo();
			 this.darRespuestaFinal();
		}
		
	}

	public void puedeEstacionar(String patente) throws ExcepcionPersonalizada {
		if(!this.tieneCreditoSuficienteParaEstacionar()) {
				throw new ExcepcionPersonalizada("Saldo insuficiente. Estacionamiento no permitido");
		}
		if(!this.estaEnZonaDeEstacionamiento()) {
				throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
		}
		//Creo que es responsabilidad del SEM verificar esta condicion 		
		if(this.hayEstacionamientoCon(patente)) { //Porque la app verifica el credito y la zona segun el enunciado
				throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
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
		this.saldoAcreditado = this.saldoAcreditado - this.calcularCreditoAPagar();
	}
	public int getHoraInicio() {
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
	   return this.horaMaximaDeEstacionamiento() - this.getHoraInicio();
   }
   public boolean puedePagarHastaFinDeFranjaHoraria() {
       return this.cantidadDeHorasSegunSaldo() >= this.cantidadDeHorasMaximas();
    	
    }
   public void setModoGps(ModoGps modo) {
   		this.gps = modo;
   }
   
	
   public void darRespuestaFinal() {
			System.out.print("!---------------------------------!"
			 + "                                        "
			 + "Su estacionamiento fue dado de baja con exito."
			 + "Hora exacta: " + this.getHoraInicio()
			 + "Hora fin: " + this.horaFinal()
			 + "Duracion: " + (this.horaFinal() - this.getHoraInicio())
			 + "Costo: " + this.calcularCreditoAPagar()
			 + "!---------------------------------!");
   }
   public void darRespuestaInicial() {
		System.out.print("!---------------------------------!"
				 + "Su estacionamiento fue dado de alta con exito."
				 + "Hora exacta: " + this.getHoraInicio()
				 + "Hora fin: " + "Pendiente"
				 + "La hora fin quedara establecida segun la cantidad de horas maximas equivalentes a su saldo acreditado"
				 + "!---------------------------------!");					
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
    
 

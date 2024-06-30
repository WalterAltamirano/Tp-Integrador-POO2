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
	private EstrategiaGPS gps;
    private EstadoApp estado;
    private Usuario usuario;
    
    //Constructor
	public AplicacionSEM(Modo modo, EstadoApp estado, SEM sistemaDeEstacionamiento, Usuario usuario,Integer nroDeCelular,EstrategiaGPS gps) {
		super();
		this.saldoAcreditado = 0;
		this.numeroDeCelular = nroDeCelular;
		this.modo = modo;
		this.estado = estado;
		this.sistemaEstacionamiento = sistemaDeEstacionamiento;
		this.usuario = usuario;
		this.gps = gps;
	}
	
	//De entrada no tendria el celular, sino que se establece una vez iniciado el estacionamiento
	public AplicacionSEM(Modo modo, EstadoApp estado, SEM sistemaDeEstacionamiento, Usuario usuario,EstrategiaGPS gps) {
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
	public void iniciarEstacionamiento(Integer numeroDeCelular, String patente)  {
		try {	
			this.puedeEstacionar(patente);
		}
		catch(ExcepcionPersonalizada e) {
			e.printStackTrace();
		}
		this.setNumeroCelular(numeroDeCelular);
		this.getSistemaEstacionamiento()
		.registrarEstacionamiento(this.instanciaDeEstacionamiento(numeroDeCelular, patente));
		this.darRespuestaInicial();
	}
	
	//Usaria el numero que fue asignado? o el enunciado dice que se envia por parametro?
	public void finalizarEstacionamiento(Integer numeroDeCelular) {
		if(!this.hayEstacionamientoCon(numeroDeCelular)) {
			 this.getSistemaEstacionamiento().finalizarEstacionamientoCon(numeroDeCelular);
			 this.darRespuestaFinal();
			 this.descontarSaldo();
		}
		
	}

	public void puedeEstacionar(String patente) throws ExcepcionPersonalizada {
		if(!this.tieneCreditoSuficienteParaEstacionar()) {
				throw new ExcepcionPersonalizada("No hay credito suficiente");
		}
		if(!this.estaEnZonaDeEstacionamiento()) {
				throw new ExcepcionPersonalizada("El usuario no esta en una zona de estacionamiento");
		}
				
		if(this.hayEstacionamientoCon(patente)) {
				throw new ExcepcionPersonalizada("Ya hay un estacionamiento vigente con la patente dada");
		}
	}
	
	public void elegirModo(Modo modo) {
		this.setModoApp(modo);
		this.getModo().avisoDeCambio();
	}

	public void elegirModoGps(EstrategiaGPS gps) {
		this.gps = gps;
	}
	
	public void cargarSaldo(double saldoACargar) {
		this.saldoAcreditado = this.saldoAcreditado + saldoACargar;
	}
	
	public double consultarSaldo() {
		return this.getCredito();
	}
	public void asignarCelular(Integer numeroDeCelular) {
		this.numeroDeCelular = numeroDeCelular;
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


	public void alertaInicioDeEstacionamiento() {
		this.getModo().notificarAlertaDeInicioDeEstacionamiento(this);
	}
	public void alertaFinDeEstacionamiento() {
		this.getModo().notificarAlertaDeFinDeEstacionamiento(this);
	}
	public void pasoACaminando() {
		this.getModo().inicioDeEstacionamiento(this); //Nuevo
	}
	public EstrategiaGPS getGps() {
		return this.gps;
	}
	public boolean hayEstacionamientoCon(String patente) {
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
	public boolean estaEnZonaDeEstacionamiento() { //hmhmmh
	    return this.elGpsEstaEncendido();
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
	public boolean elGpsEstaEncendido() {		//Booleano feo
		return this.gps.getEstaEncendido();
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
   public void setEstrategiaGPS(EstrategiaGPS estrategia) {
   		this.gps = estrategia;
   }
   
   public void setNumeroCelular(Integer numeroDeCelular) {
		this.numeroDeCelular = numeroDeCelular;
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
//	!-------------------------------------------------------------------------!
 
}

    /*
     Anotaciones:
      *Creo que el usuario deberia tener un mensaje donde pueda elegir el modo 
        de la aplicacion y tambien otros dos mensajes que indiquen que quieran
        prender el gps o apagarlo
        */
    
 

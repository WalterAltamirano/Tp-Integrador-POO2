package clasesIan;

public class AplicacionSEM implements MovementSensor{
    private EstadoApp estadoActual;
    
	public AplicacionSEM() {
		estadoActual = new Caminando(); //Inicia caminando
	}
	
	public void inicioEstacionamiento(int numeroCelular,String patente) {}
	
	public void finalizarEstacionamiento(int numeroCelular) {}
	
	public void elegirModo(/*Modo modo*/) {}
	
	public void elegirEstadoGPS(/*EstadoGPS estado*/) {}
	
	public void cargarSaldo(double saldoACargar) {}

	
	//Metodos para cambiar de estado
	void setEstado(EstadoApp estado) {
		this.estadoActual = estado;
	}
	@Override
	public void driving() {
		estadoActual.manejando(this);
	}
	@Override
	public void walking() {
		estadoActual.caminando(this);
		
	}
	
	void setEstadoGPS(/*EstadoGPS estado*/) {
	}

	//Para saber el numero.
	int getNumero() {
		return 0;
	}

	public Object getModo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void alertaInicioDeEstacionamiento() {
		// TODO Auto-generated method stub
		
	}

	public void alertaFinDeEstacionamiento() {
		// TODO Auto-generated method stub
		
	}
}

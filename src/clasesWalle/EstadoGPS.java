package clasesWalle;

public abstract class EstadoGPS {

	private boolean estaEncendido;
	
	public boolean getEstaEncendido() {
		return this.estaEncendido;
	}
	
	protected void setEstaEncendido(boolean valor) {
		this.estaEncendido = valor;
	}
	void prenderGps() {
		this.setEstaEncendido(true);
	}
	
    void apagarGps() {
    	this.setEstaEncendido(false);
    }
}

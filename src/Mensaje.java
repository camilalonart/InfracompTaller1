
public class Mensaje {
	private int mensaje;
	private boolean respondido;
	
	public Mensaje(int pmensaje) {
		mensaje = pmensaje;
	}
	public int getMensaje() {
		return mensaje;
	}
	public void setMensaje(int mensaje) {
		this.mensaje = mensaje;
	}
	public boolean isRespondido() {
		return respondido;
	}
	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}
	@Override
	public String toString() {
		return " "+ mensaje;
	}
}

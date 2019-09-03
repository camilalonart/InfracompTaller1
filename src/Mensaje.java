
public class Mensaje {
	private int mensaje;
	private boolean respondido;
	private Cliente c;
	public Mensaje(int pmensaje, Cliente pc) {
		mensaje = pmensaje;
		c=pc;
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
//		c.setNumConsultas(c.getNumConsultas() -1);
	}
	
	public synchronized void hola()
	{
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return " "+ mensaje;
	}
	public Cliente getC() {
		return c;
	}
	public void setC(Cliente c) {
		this.c = c;
	}
}

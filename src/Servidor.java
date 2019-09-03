public class Servidor extends Thread {
	private Buffer buffer;
	private String nombre;
    public Servidor (Buffer b, String n ){
    	buffer = b;
    	nombre = n;
    }
	public Buffer getBuffer() {
		return buffer;
	}
	public void setBuffer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	@Override
    public void run(){
		while(true){
			Mensaje m = buffer.sacarMensajes();
			if(m != null){
				synchronized (m){
					m.setRespondido(true);
					buffer.setMensajesRestantes(buffer.getMensajesRestantes()-1);
					System.out.println("Al servidor "+ nombre+" llego el mensaje: "+ (m.getMensaje()));
					m.notify();
				}
			}
			if(buffer.getMensajesRestantes() == 0){
				try {
					System.out.println("El servidor "+ nombre+" se encuentra en espera de nuevos mensajes...");
					join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			yield();
		}
    }
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}

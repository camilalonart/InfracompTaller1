public class Servidor extends Thread {
	private Buffer buffer;
    public Servidor (Buffer b ){
    	buffer = b;
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
					buffer.setMensajesFinalizados(buffer.getMensajesRestantes()-1);
					System.out.println("Respuesta : "+ (m.getMensaje()));
					m.notify();
				}
			}
			if(buffer.getMensajesRestantes() == 0){
				try {
					System.out.println("TERMINO serv. "+ this.hashCode());
					join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			yield();
		}
    }
}

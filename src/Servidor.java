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
			synchronized(buffer)
			{
				if (!buffer.getBuffer().isEmpty()) {
					Mensaje m = buffer.getBuffer().remove(0);
					buffer.notifyAll();
					
					//Mensaje m = buffer.sacarMensajes();
					if(m != null){
						System.out.println("Se saco el mensaje" + m.toString());
						synchronized (m){
							m.setRespondido(true);
							if(m.getC().getNumConsultas() == 0)
							{
								buffer.setNumClientes(buffer.getNumClientes() - 1);
							}
							m.notify();

						}
					}
				}
				if(buffer.getNumClientes() == 0){
					System.out.println("El servidor "+ nombre+" termino.");
					break;
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

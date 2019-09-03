
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
    
	public void enviarMensaje() {}
}

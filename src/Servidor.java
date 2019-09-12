import java.util.Random;

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
	
	private int numAleatorio(int min, int max) 
	{
		if (min >= max)
		{
			throw new IllegalArgumentException("el rango no esta bien, hay que cambiarlo");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public void run(){
		while(true){
			synchronized(buffer)
			{
				if (!buffer.getBuffer().isEmpty()) {
					Mensaje m = buffer.getBuffer().remove(0);
					buffer.notifyAll();
					
					if(m != null){
						String respuesta = "Respuesta para cedula " + m.toString() + "\n";
						int n = numAleatorio(1, 4);
						respuesta += "Numero empleos: " + n + "\n";
						for (int i = 0; i < n; i++) 
						{
							//#empleos, rango de anios empleo, nit del empleador, salario
							respuesta += "Rango de tiempo empleo " + (i+1) + ": " + numAleatorio(1, 5) + " anios\n";
							String nitCompleto = numAleatorio(10,99) + "," + (numAleatorio(100,999)) + "," + (numAleatorio(100,999)) + "-3";
							respuesta += "NIT empleador: " + nitCompleto + "\n";
							respuesta += "Salario: " + numAleatorio(7000000,12000000) + " COP\n";
						}
						System.out.println(respuesta);
						System.out.println();
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
					System.out.println("El servidor " + nombre + " termino.");
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

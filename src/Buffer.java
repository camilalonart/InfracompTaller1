import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class Buffer {
	private static final String RUTADATOS = "./data/data.txt";
	private ArrayList<Mensaje> buffer;
	private int capacidad;
	private static int mensajesRestantes;
	private static int numClientes;

	public Buffer(int pCapacidad) {
		capacidad = pCapacidad;
		buffer = new ArrayList<Mensaje>();
		mensajesRestantes = 0;
	}

	public ArrayList<Mensaje> getBuffer() {
		return buffer;
	}

	public void setBuffer(ArrayList<Mensaje> buffer) {
		this.buffer = buffer;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public void setMensajesRestantes(int pmensajesRestantes) {
		mensajesRestantes = pmensajesRestantes;
	}

	public synchronized void setNumClientes(int pNumClientes) {
		numClientes = pNumClientes;
	}

	public static String getRutadatos() {
		return RUTADATOS;
	}

	public synchronized int getNumClientes() {
		return numClientes;
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

	public void agregarMensaje(Cliente c) {
		int cedula = numAleatorio(1000000000,1999999999);
		Mensaje m = new Mensaje(cedula, c);
		while(!m.isRespondido())
		{

			synchronized (buffer) {

				if (buffer.size() < capacidad) {
					synchronized(m)
					{
					buffer.add(m);
					System.out.println("Se agrega el la consulta con cedula: " + m.toString());
						try {
							m.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}						
					}

				} 
				else 
				{
					System.out.println("La consulta de mensaje con cedula" + m.toString() + " no se pudo agregar porque esta lleno el buffer ");
					synchronized(c) {
						try 
						{
							c.wait();
						} 
						catch (InterruptedException e) 
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(RUTADATOS));
			int numServidores = Integer.parseInt(br.readLine().split(":")[1]);
			int capacidadBuffer = Integer.parseInt(br.readLine().split(":")[1]);
			numClientes = Integer.parseInt(br.readLine().split(":")[1]);
			Buffer b = new Buffer(capacidadBuffer);
			System.out.println("La capacidad del buffer es: " + capacidadBuffer);
			System.out.println("El numero de servidores: " + numServidores);
			System.out.println("-------------------------------------------------");

			for (int i = 0; i < numClientes; i++) {
				int n = Integer.parseInt(br.readLine());
				Cliente c = new Cliente(n, b);
				c.start();
			}

			br.close();
			for (int i = 0; i < numServidores; i++) {
				Servidor s = new Servidor(b, "Servidor" + i);
				s.start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static int getMensajesRestantes() {
		return mensajesRestantes;
	}

}

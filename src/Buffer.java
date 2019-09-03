import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

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

	public static int getNumClientes() {
		return numClientes;
	}

	public void agregarMensaje(Cliente c) {
		Mensaje m = new Mensaje((int) (Math.random() * 20), c);
		while(!m.isRespondido())
		{

			synchronized (buffer) {

				if (buffer.size() < capacidad) {
					buffer.add(m);
					System.out.println("Se agrega el mensaje" + m.toString());
					//				m.hola(); RAROOOOO

				} else {
					System.out.println("El mensaje" + m.toString() + " no se pudo agregar porque esta lleno el buffer ");
					try {
						buffer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Mensaje sacarMensajes() {
		Mensaje m = null;
		synchronized (buffer) {

			if (!buffer.isEmpty()) {
				m = buffer.remove(0);
				buffer.notifyAll();
				synchronized(m)
				{
					m.notify();
					if(m.getC().getNumConsultas() == 0)
					{
						setNumClientes(Buffer.getNumClientes() - 1);
					}
					System.out.println("Se saco el mensaje" + m.toString());

				}
			}
		}
		return m;
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

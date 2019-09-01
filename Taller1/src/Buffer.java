import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class Buffer {
	private static final String RUTADATOS = "bla bla bla";
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
	
	public static void setMensajesRestantes(int mensajesRestantes) {
		mensajesRestantes = mensajesRestantes;
	}

	public static void setNumClientes(int pNumClientes) {
		numClientes = pNumClientes;
	}

	public static String getRutadatos() {
		return RUTADATOS;
	}
	public static int getNumClientes() {
		return numClientes;
	}
	
	public void agregarConsulta() {
		Mensaje m = null;
		while(m == null){
			synchronized (buffer) {
				if(buffer.size() < capacidad){
					m = new Mensaje((int)(Math.random()*20));
					buffer.add(m);
					System.out.println("Mensaje enviado");
				}
				else{
					System.out.println("No se pudo agregar porque esta lleno el buffer ");
					try {
						buffer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	} 

	public static void main(String[] args) {
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(RUTADATOS));
			int numServidores =  Integer.parseInt(br.readLine().split(":")[1]);
			int capacidadBuffer =  Integer.parseInt(br.readLine().split(":")[1]);
			numClientes =  Integer.parseInt(br.readLine().split(":")[1]);
			int cantConsultas = 0;

			for(int i = 0; i < numClientes; i++){
				int n = Integer.parseInt(br.readLine());
				Cliente c = new Cliente(n);
				c.start();
			}
			for(int i = 0; i < numServidores; i++){
				Servidor s = new Servidor(this);
				s.start();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		//leer datos y start a servidores y clientes
	}
	
}

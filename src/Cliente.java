public class Cliente extends Thread{

    private int numConsultas;
    private Buffer buff;
    private int tamanoinicial;
    
    public Cliente(int pNumConsultas, Buffer b)
    {
    	tamanoinicial = pNumConsultas;
        numConsultas = pNumConsultas;
        buff = b;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < tamanoinicial ; i++) 
        {
            buff.agregarMensaje(this);
        }
    }

	public int getNumConsultas() {
		return numConsultas;
	}

	public void setNumConsultas(int numConsultas) {
		this.numConsultas = numConsultas;
	}

	public Buffer getBuff() {
		return buff;
	}

	public void setBuff(Buffer buff) {
		this.buff = buff;
	}

}
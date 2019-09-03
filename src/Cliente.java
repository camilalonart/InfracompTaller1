public class Cliente extends Thread{

    private int numConsultas;
    private Buffer buff;

    public Cliente(int pNumConsultas, Buffer b)
    {
        numConsultas = pNumConsultas;
        buff = b;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < numConsultas; i++) 
        {
            buff.agregarMensaje();
        }
        Buffer.setNumClientes(Buffer.getNumClientes() - 1);
    }

}
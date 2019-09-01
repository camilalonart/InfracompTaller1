public class Cliente extends Thread{

    private int numConsultas;
    private static Buffer buff;

    public Cliente(int pNumConsultas)
    {
        numConsultas = pNumConsultas;
    }

    @Override
    public void run()
    {
        for (int i = 0; i < numConsultas; i++) 
        {
            buff.agregarConsulta();
        }
        buff.setNumClientes(Buffer.getNumClientes() - 1);
    }

}
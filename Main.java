import ASlib.Distr.Trama;
import ASlib.Distr.Red;
import ASlib.Distr.Conector;

public class Main {
    public static void main(String[] args) {

        Servidor servidor = new Servidor(1);
        servidor.start();

        Coordinador coordinador = new Coordinador(2);
        coordinador.start();

        Conector conector = Red.Conectar(0);

        Cliente[] clientes = new Cliente[Sim.N_CLIENTES];
        for (int i = 0; i < Sim.N_CLIENTES; i++) {
            clientes[i] = new Cliente(i + 3);
            clientes[i].start();
        }

        for (int i = 0; i < Sim.N_CLIENTES; i++) {
            clientes[i].Join();
            System.out.println("Termina " + clientes[i].id);
        }

        Trama trama = new Trama();
        trama.origen = 0;
        trama.destino = -1;
        trama.mensaje = "END"; 
        System.out.println("Envio: " + trama);
        //Enviar a todos.
        conector.EnviarBroadcast(trama);

        servidor.Join();
        coordinador.Join();

        Red.Desconectar(conector);
        System.exit(0);
    }
}
import ASlib.Distr.Red;
import ASlib.Distr.Trama;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Coordinador extends Proceso {

    public static Coordinador INSTANCIA;
    private final Queue<Trama> tramas = new ConcurrentLinkedDeque<>();

    private EstadoCoordinador estado;


    public Coordinador(int nt) {
        super(nt);
        INSTANCIA = this;
    }

    public void run() {
        trazador.Print("Coordinador");

        //Inicio el primer estado
        estado = new Estado1();
        Trama trama;
        boolean end = false;

        while(!end) {
            
            trama = Recibir();

            
            if (trama.mensaje.equals("END"))
                end=true;

            String operacion = extraerOperacion(trama);

            if (operacion.equals("REQ")) {
                estado.manejoReq(trama);
            }
            if (operacion.equals("REL")) {
                estado.manejoRel(trama);
            }
        }

        trazador.Print("FIN");
        Red.Desconectar(conector);
    }

    private static String extraerOperacion(Trama trama) {
        String[] partes = trama.mensaje.split(" ");
        return partes[0];
    }

    public void Enviar_CONF(int idCliente) {
        Trama trama = new Trama();
        trama.origen = super.id;
        trama.destino = idCliente;
        trama.mensaje = "CONF"; 
        Enviar(trama);
    }
    private interface EstadoCoordinador {

        void manejoReq(Trama trama);

        void manejoRel(Trama trama);

    }

    private final class Estado1 implements EstadoCoordinador {

        @Override
        public void manejoReq(Trama trama) {
            Enviar_CONF(trama.origen);
            estado = new Estado2(); // Cambio a estado 2
        }

        @Override
        public void manejoRel(Trama trama) {
            trazador.Print("Error, he recibido REL");// No deberia pasar
        }
    }

    private final class Estado2 implements EstadoCoordinador {

        @Override
        public void manejoReq(Trama trama) {
            tramas.add(trama);
        }

        @Override
        public void manejoRel(Trama trama) {
            Trama tr;
            if ((tr = tramas.poll()) != null)
                Enviar_CONF(tr.origen);
        }
    }

}

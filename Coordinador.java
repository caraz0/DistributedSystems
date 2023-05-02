import ASlib.Distr.Red;
import ASlib.Distr.Trama;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;

public class Coordinador extends Proceso {

    public static Coordinador INSTANCIA;
    private final Queue<Integer> ids = new ConcurrentLinkedDeque<>();

    private final Semaphore mutex = new Semaphore(0);

    public Coordinador(int nt) {
        super(nt);
        INSTANCIA = this;
    }

    public void run() {
        trazador.Print("Coordinador");

        startReadLoop();

        Trama trama;
        while(true) {
            trama = Recibir();

            if (trama.getMensaje().equals("END"))
                break;

            String operacion = extraerOperacion(trama);
            if (operacion.equals("REQ")) {
                ids.add(trama.origen);
            }
            if (operacion.equals("REL")) {
                mutex.release();
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
        Trama trama = new Trama(super.id, idCliente, "CONF");
        Enviar(trama);
    }

    public void startReadLoop() {
        new Thread(() -> {
            while (true) {
                Integer idCliente = ids.poll();
                if (idCliente == null) {
                    Pausa(10);
                    continue;
                }

                Enviar_CONF(idCliente);
                acquireMutex();
            }
        }).start();
    }

    private void acquireMutex() {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

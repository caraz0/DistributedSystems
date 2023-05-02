package ASlib.Distr;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;

public class Conector
    {
    public int id;
    private Semaphore b_recibir = new Semaphore(0);
    private Queue<Trama> tramas = new LinkedBlockingQueue<>();

    public Conector(int id) {this.id = id;}

    public boolean Enviar(Trama trama)
        {
        return (Red.Enviar(trama));
        }
    void Depositar(Trama trama)
        {
        if (trama.mensaje.equals("WHO"))
            {
            Trama respuesta = new Trama();
            respuesta.origen = id;
            respuesta.destino = trama.origen;
            respuesta.mensaje = "IAM "+id;
            Enviar(respuesta);
            }
        else
            {
            Trama t = new Trama(trama);
            tramas.add(t);
            b_recibir.release();
            }
        }

    public Trama Recibir()
        {
        try {b_recibir.acquire();} catch (Exception ex) {System.err.println(ex.getMessage()); System.exit(666);}
        Trama trama = tramas.remove();
        return (trama);
        }
    }

package ASlib.Distr;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;

public class Conector
    {
    public int id;
    private Semaphore b_recibir = new Semaphore(0);
    private Queue<Trama> tramas = new LinkedBlockingDeque<>();

    public Conector(int id) {this.id = id;}

    public boolean Enviar(Trama trama)
        {
        return (Red.Enviar(trama));
        }
    public boolean EnviarBroadcast(Trama trama)
        {
        return (Red.EnviarBroadcast(trama));
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
            Trama t = trama.Clonar();
            tramas.add(t);
            b_recibir.release();
            }
        }

    public Trama Recibir()
        {
        try {b_recibir.acquire();} catch (Exception ex) {System.err.println(ex.getMessage()); System.exit(666);}
        Trama trama = tramas.poll();
        return (trama);
        }
    }

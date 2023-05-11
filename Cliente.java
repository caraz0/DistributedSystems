import ASlib.Distr.Red;
import ASlib.Distr.Trama;


public class Cliente extends Proceso {

    public Cliente(int nt) {
        super(nt);
    }

    public void run() {
        trazador.Print("Cliente");

        for (int i = 0; i < Sim.N_OPERACIONES; i++) {

            enviarReqACoordinador();
            //Pausa(10);

            Trama tr = Recibir();
            if (!tr.mensaje.equals("CONF")) {
                trazador.Print("Error");
                return;
            }

            intercambioMensajes();
        }

        trazador.Print("FIN");
        Red.Desconectar(conector);
    }

    private void intercambioMensajes() {
        Enviar_READ();
        //Pausa(10);

        Trama tr = Recibir();
        Enviar_WRITE(extraerRecurso(tr) + 1);
        //Pausa(10);

        tr = Recibir();
        //Pausa(10);
        Enviar_REL();
    }

    private int extraerRecurso(Trama tr) {
        String[] partes = tr.mensaje.split(" ");
        return Integer.parseInt(partes[partes.length - 1]);
    }

    public void Enviar_WRITE(int v) {
        Trama tr = new Trama();
        tr.destino = Servidor.INSTANCIA.id;
        tr.origen = super.id;
        tr.mensaje = "WRITE " + v;
        Enviar(tr);

    }

    public void Enviar_READ() {
        Trama envio = new Trama();
        envio.destino = Servidor.INSTANCIA.id;
        envio.origen = super.id;
        envio.mensaje = "READ";
        Enviar(envio);
    }

    public void enviarReqACoordinador() {
        Trama trama = new Trama();
        trama.destino = Coordinador.INSTANCIA.id;
        trama.origen = super.id;
        trama.mensaje = "REQ";
        Enviar(trama);
    }

    public void Enviar_REL() {
        Trama trama = new Trama();
        trama.destino = Coordinador.INSTANCIA.id;
        trama.origen = super.id;
        trama.mensaje = "REL";
        Enviar(trama);
    }


}

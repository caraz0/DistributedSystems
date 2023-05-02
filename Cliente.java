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
            Pausa(10);

            Trama tr = Recibir();
            if (!tr.getMensaje().equals("CONF")) {
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
        Pausa(10);

        Trama tr = Recibir();
        Enviar_WRITE(extraerRecurso(tr) + 1);
        Pausa(10);

        tr = Recibir();
        Pausa(10);
        Enviar_REL();
    }

    private int extraerRecurso(Trama tr) {
        String[] partes = tr.getMensaje().split(" ");
        return Integer.parseInt(partes[partes.length - 1]);
    }

    public void Enviar_WRITE(int v) {
        Trama tr = new Trama(super.id, Servidor.INSTANCIA.id, "WRITE " + v);
        intentarEnvio(tr);

        trazador.Print("Envio " + tr);
    }

    public void Enviar_READ() {
        Trama envio = new Trama(super.id, Servidor.INSTANCIA.id, "READ");
        intentarEnvio(envio);

        trazador.Print("Envio " + envio);
    }

    public void enviarReqACoordinador() {
        Trama trama = new Trama(super.id, Coordinador.INSTANCIA.id, "REQ");
        intentarEnvio(trama);

        trazador.Print("Envio " + trama);
    }

    public void Enviar_REL() {
        Trama trama = new Trama(super.id, Coordinador.INSTANCIA.id, "REL");
        intentarEnvio(trama);

        trazador.Print("Envio " + trama);
    }


}

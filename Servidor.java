import ASlib.Distr.Red;
import ASlib.Distr.Trama;

public class Servidor extends Proceso {

    public static Servidor INSTANCIA;

    private static int recurso = 0;

    public Servidor(int nt) {
        super(nt);
        INSTANCIA = this;
    }

    public void run() {

        trazador.Print("Servidor");

        while (true) {
            Trama tr;
            tr = Recibir();

            if (tr.getMensaje().equals("END"))
                break;

            String operacion = extraerOperacion(tr);

            if (operacion.equals("READ"))
                enviaRead(tr);
            else if (operacion.equals("WRITE"))
                enviarWrite(tr);
        }

        trazador.Print("Recurso final: " + recurso);
        trazador.Print("FIN");
        Red.Desconectar(conector);
    }

    private void enviaRead(Trama tr) {
        Trama read = new Trama(super.id, tr.getOrigen(), "VAL " + recurso);
        Enviar(read);
    }

    private void enviarWrite(Trama tr) {
        recurso = extraerRecurso(tr);
        Trama write = new Trama(super.id, tr.getOrigen(), "VAL " + recurso);
        Enviar(write);
    }

    private String extraerOperacion(Trama tr) {
        String[] partes = tr.mensaje.split(" ");
        return partes[0];
    }

    private int extraerRecurso(Trama trama) {
        String[] partes = trama.mensaje.split(" ");
        return Integer.parseInt(partes[partes.length - 1]);
    }

}

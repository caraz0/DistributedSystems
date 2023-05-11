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

        boolean end = false;
        trazador.Print("Servidor");
    
        while (!end) {
            Trama tr;
            tr = Recibir();
            

            if (tr.mensaje.equals("END"))   
                end = true;

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
        Trama trama = new Trama();
        trama.destino = tr.origen;
        trama.origen = super.id;
        trama.mensaje = "VAL " + recurso; 
        Enviar(trama);
    }

    private void enviarWrite(Trama tr) {
        recurso = extraerRecurso(tr);
        Trama trama = new Trama();
        trama.destino = tr.origen;
        trama.origen = super.id;
        trama.mensaje = "OK " + recurso; 
        Enviar(trama);
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

import ASlib.*;
import ASlib.Distr.Conector;
import ASlib.Distr.Red;
import ASlib.Distr.Trama;

public abstract class Proceso extends Hilo {

    public int id;
    protected Trazador trazador;
    protected Conector conector;

    public Proceso(int nt) {
        id = (int) getId();
        trazador = new Trazador(nt, id + "");
        conector = Red.Conectar(id);
    }

    public Trama Recibir() {
        Trama trama;

        while((trama = conector.Recibir()) == null)
            Pausa(100);

        trazador.Print("Recibo " + trama);
        return trama;

    }

    public void Enviar(Trama trama) {
        trazador.Print("Envio " + trama);
        intentarEnvio(trama);
    }

    public void intentarEnvio(Trama trama) {
        boolean envioCorrecto = conector.Enviar(trama);
        if (!envioCorrecto) {
            Pausa(10);
            intentarEnvio(trama);
        }
    }

}

package ASlib.Distr;

public class Trama implements Cloneable {
    public int origen;
    public int destino;
    public String mensaje;

    public Trama() {

    }

    public Trama(int origen, int destino, String mensaje) {
        this.origen = origen;
        this.destino = destino;
        this.mensaje = mensaje;
    }

    public Trama(Trama trama) {
        this(trama.getOrigen(), trama.getDestino(), trama.mensaje);
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String toString() {
        return "o:" + origen + " d:" + destino + " m:" + mensaje;
    }

}
package ASlib.Distr;

import java.util.Queue;
import java.util.ArrayList;
import java.util.Vector;

public class Red
    {
    private static ArrayList<Conector> conectores = new ArrayList<>();

    public static Conector Conectar(int id)
        {
        for (Conector c : conectores)
            {
            if (c.id == id) return(null);
            }

        Conector conector = new Conector(id);
        conectores.add(conector);
        return(conector);
        }
    public static boolean Desconectar(Conector conector)
        {
        int i = 0;
        for (Conector c : conectores)
            {
            if (c.id == conector.id)
                {
                conectores.remove(i);
                return(true);
                }
            i++;
            }
        return(false);        
        }
    static boolean Enviar(Trama trama)
        {
        for (Conector conector : conectores)
            {
            if (conector.id == trama.destino)
                {
                conector.Depositar(trama);
                return(true);
                }
            }
        return(false);
        }
    }


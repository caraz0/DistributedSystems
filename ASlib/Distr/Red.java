package ASlib.Distr;

import java.util.Queue;
import java.util.ArrayList;

public class Red
    {
    private static ArrayList<Conector> conectores = new ArrayList<>();

    public static synchronized Conector Conectar(int id)
        {
        for (Conector c : conectores)
            {
            if (c.id == id) return(null);
            }

        Conector conector = new Conector(id);
        conectores.add(conector);
        return(conector);
        }
    public static synchronized boolean Desconectar(Conector conector)
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
    public static synchronized boolean Enviar(Trama trama)
        {
        if (trama.destino == -1)
            {
            for (Conector conector : conectores)
                conector.Depositar(trama);
            return(true);
            }

        for (Conector conector : conectores)
            {
            if ((trama.destino == -1) || (conector.id == trama.destino))
                {
                conector.Depositar(trama);
                return(true);
                }
            }
        return(false);
        }
    public static synchronized boolean EnviarBroadcast(Trama trama)
        {
        for (Conector conector : conectores)
            {
                
            conector.Depositar(trama);
                
            }
        return(true);
        }
    }


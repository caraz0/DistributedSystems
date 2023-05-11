package ASlib.Distr;

public class Trama implements Cloneable
    {
    public int origen;
    public int destino;
    public String mensaje;

    public String toString()
        {
        String s = "o:"+origen+" d:"+destino+ " m:"+mensaje;
        return(s);
        }

    public Object clone()throws CloneNotSupportedException
        {  
		return super.clone();  
        }  

    Trama Clonar()
        {
        Trama trama = null;
        
        try
            {
            trama = (Trama)this.clone();
            }
        catch(CloneNotSupportedException e)
            {
            System.err.println("Error "+e.getMessage());
            System.exit(666);
            }
        return (trama);
        }
    }

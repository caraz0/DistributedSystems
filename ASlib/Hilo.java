package ASlib;

public abstract class Hilo extends Thread
	{
   	public void Join()
        {
        try {join();}
        catch(Exception e) 
			{
			System.err.println("Error en el join");
			System.err.println(e.getMessage());
			System.exit(666);			
			}
        }

    public static void Wait(Object cerrojo)
        {
        try
            {
            cerrojo.wait();
            }
        catch (Exception e) 
            {
            System.err.println(e);
            System.exit(-1);
            }  
        }

	public static void Pausa(int duracion)
        {
		try
            {
            Thread.sleep(duracion);
            }
        catch(InterruptedException e)
            {
            System.err.println(e);
            System.exit(-1);
            }  
        }

    public static void pausa(int min, int max)
        {
        int duracion = min + (int)(Math.random()*max);
		Pausa(duracion);
        }
	}

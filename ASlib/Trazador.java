package ASlib;

public class Trazador
	{
	String tabs;

	public Trazador(int ntabs, String etiqueta)
		{
		tabs = "";
		for (int i = 0; i<ntabs; i++) tabs+="\t";
		tabs +="["+etiqueta+"]";
		}
	public Trazador(int ntabs, int n)
		{
		tabs = "";
		for (int i = 0; i<ntabs; i++) tabs+="\t";
		tabs +="["+n+"]";
		}


	public void Print(String s)
		{
		String msj = String.format("%s%s",tabs,s);
		System.out.println(msj);
		}
	}


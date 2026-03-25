package server.exceptions;

public class ProdottoNotAvailable extends Exception 
{
	public ProdottoNotAvailable()
	{
		super("Prodotto non disponibile");
	}

	public ProdottoNotAvailable(String message) 
	{
		super(message);
	}
}

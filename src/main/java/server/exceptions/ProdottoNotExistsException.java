package server.exceptions;

public class ProdottoNotExistsException extends Exception 
{
	public ProdottoNotExistsException()
	{
		super("Prodotto non esistente");
	}

	public ProdottoNotExistsException(String message) 
	{
		super(message);
	}
}

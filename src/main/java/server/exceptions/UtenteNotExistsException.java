package server.exceptions;

public class UtenteNotExistsException extends Exception 
{
	public UtenteNotExistsException()
	{
		super("Utente non esistente");
	}

	public UtenteNotExistsException(String message) 
	{
		super(message);
	}
}

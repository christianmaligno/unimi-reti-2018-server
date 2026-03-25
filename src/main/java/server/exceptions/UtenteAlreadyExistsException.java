package server.exceptions;

public class UtenteAlreadyExistsException extends Exception
{
	public UtenteAlreadyExistsException()
	{
		super("User already exists");
	}
	
	public UtenteAlreadyExistsException(String message)
	{
		super(message);
	}
}

package server.exceptions;

public class OwnProductPurchaseException extends Exception 
{
	public OwnProductPurchaseException()
	{
		super("Non puoi acquistare un tuo prodotto");
	}
	
	public OwnProductPurchaseException(String message)
	{
		super(message);
	}
}

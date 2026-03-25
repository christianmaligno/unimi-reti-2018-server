package server.commands;

import java.math.BigDecimal;

import server.exceptions.UtenteNotExistsException;
import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;

public class AggiungiProdottoCommand extends Command 
{
	public AggiungiProdottoCommand(HttpRequest request, Http http, Model model)
	{
		super(request, http, model);
	}
	
	@Override
	protected HttpResponse createResponse(HttpRequest request, Model model) 
	{
		String nome = request.getParameter("nome");
		String prezzo = request.getParameter("prezzo");
		String username = request.getParameter("username");
		
		HttpResponse response;
		try 
		{
			model.aggiungiProdotto(nome, new BigDecimal(prezzo), username);
			response = createOkHttpResponse();
		}
		catch (NumberFormatException e)
		{
			response = createBadRequestHttpResponse("Valore del prezzo non valido");
		} 
		catch (UtenteNotExistsException e) 
		{
			response = createBadRequestHttpResponse(e.getMessage());
		}
		
		return response;
	}

}

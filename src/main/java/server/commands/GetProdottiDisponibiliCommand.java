package server.commands;

import java.util.ArrayList;

import com.google.gson.Gson;

import server.exceptions.UtenteNotExistsException;
import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;
import server.model.dao.Prodotto;

public class GetProdottiDisponibiliCommand extends Command {

	public GetProdottiDisponibiliCommand(HttpRequest request, Http http, Model model) 
	{
		super(request, http, model);
	}

	@Override
	protected HttpResponse createResponse(HttpRequest request, Model model) 
	{
		String username = request.getParameter("username");
		
		HttpResponse response;
		try
		{
			ArrayList<Prodotto> prodottiDisponibili = model.getProdottiDisponibili(username);
			Gson gson = new Gson();
			String json = gson.toJson(prodottiDisponibili);
			
			response = createOkHttpResponse(json);
		}
		catch (UtenteNotExistsException e)
		{
			response = createBadRequestHttpResponse(e.getMessage());
		}
		
		return response;
	}

}

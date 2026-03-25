package server.commands;

import java.util.ArrayList;

import com.google.gson.Gson;

import server.exceptions.UtenteNotExistsException;
import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;
import server.model.dao.Prodotto;

public class GetProdottiVendutiCommand extends Command {

	public GetProdottiVendutiCommand(HttpRequest request, Http http, Model model) {
		super(request, http, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpResponse createResponse(HttpRequest request, Model model) {
		// TODO Auto-generated method stub
		
		String username = request.getParameter("username");
		
		HttpResponse response;
		try 
		{
			ArrayList<Prodotto> prodottiVenduti = model.getProdottiVenduti(username);
			Gson gson = new Gson();
			String json = gson.toJson(prodottiVenduti);
			
			response = createOkHttpResponse(json);
		} 
		catch (UtenteNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = createBadRequestHttpResponse(e.getMessage());
		}
		
		return response;
	}

}

package server.commands;

import server.exceptions.OwnProductPurchaseException;
import server.exceptions.ProdottoNotAvailable;
import server.exceptions.ProdottoNotExistsException;
import server.exceptions.UtenteNotExistsException;
import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;

public class CompraProdottoCommand extends Command 
{

	public CompraProdottoCommand(HttpRequest request, Http http, Model model) {
		super(request, http, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpResponse createResponse(HttpRequest request, Model model) {
		// TODO Auto-generated method stub
		
		int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));
		String acquirente = request.getParameter("username");
		HttpResponse response;
		
		try 
		{
			model.compraProdotto(idProdotto, acquirente);
			response = createOkHttpResponse();
		} 
		catch (UtenteNotExistsException | OwnProductPurchaseException | ProdottoNotExistsException | ProdottoNotAvailable e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			response = createBadRequestHttpResponse(e.getMessage());
		}
		
		return response;
	}

}

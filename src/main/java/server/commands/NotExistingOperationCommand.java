package server.commands;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;

public class NotExistingOperationCommand extends Command {

	public NotExistingOperationCommand(HttpRequest request, Http http, Model model) {
		super(request, http, model);
	}

	@Override
	protected HttpResponse createResponse(HttpRequest request, Model model) 
	{
		return createNotFoundHttpResponse();
	}
}

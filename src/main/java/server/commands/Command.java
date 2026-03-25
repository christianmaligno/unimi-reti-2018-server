package server.commands;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import server.io.Http;
import server.io.HttpRequest;
import server.io.HttpResponse;
import server.model.Model;

public abstract class Command 
{
	private HttpRequest request;
	private Http http;
	private Model model;
	
	public Command(HttpRequest request, Http http, Model model)
	{
		this.request = request;
		this.http = http;
		this.model = model;
	}
	
	protected abstract HttpResponse createResponse(HttpRequest request, Model model);
	
	public void execute()
	{
		HttpResponse response = createResponse(request, model);
		
		http.sendResponse(response);
		
		try 
		{
			http.closeConnection();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected HttpResponse createOkHttpResponse()
	{
		return HttpResponse.createHttpResponse(HttpResponse.STATUS_CODE_OK);
	}
	
	protected HttpResponse createOkHttpResponse(String bodyJson)
	{
		return HttpResponse.createHttpResponse(HttpResponse.STATUS_CODE_OK, HttpResponse.CONTENT_TYPE_JSON, bodyJson);
	}
	
	protected HttpResponse createBadRequestHttpResponse(String errorMessage)
	{
		Gson gson = new Gson();
		JsonObject bodyObj = new JsonObject();
		bodyObj.addProperty("error", errorMessage);
		String bodyJson = gson.toJson(bodyObj);
		
		return HttpResponse.createHttpResponse(HttpResponse.STATUS_CODE_BAD_REQUEST, HttpResponse.CONTENT_TYPE_JSON, bodyJson);
	}
	
	protected HttpResponse createNotFoundHttpResponse()
	{
		return HttpResponse.createHttpResponse(HttpResponse.STATUS_CODE_NOT_FOUND);
	}
}

package server;

import java.io.IOException;
import java.net.Socket;

import server.commands.AggiungiProdottoCommand;
import server.commands.Command;
import server.commands.CompraProdottoCommand;
import server.commands.GetProdottiDisponibiliCommand;
import server.commands.GetProdottiVendutiCommand;
import server.commands.NotExistingOperationCommand;
import server.io.Http;
import server.io.HttpRequest;
import server.model.Model;

public class Worker extends Thread 
{
	private Socket socket;
	private Model model;
	
	public Worker(Socket socket, Model model)
	{
		this.socket = socket;
		this.model = model;
	}
	
	@Override
	public void run()
	{
		try 
		{
			Http http = new Http(socket);
			HttpRequest request = http.receiveRequest();
			
			String path = request.getPath();
			Command command;
			switch (path)
			{
				case "/AggiungiProdotto":
					command = new AggiungiProdottoCommand(request, http, model);
					break;
				case "/GetProdottiDisponibili":
					command = new GetProdottiDisponibiliCommand(request, http, model);
					break;
				case "/GetProdottiVenduti":
					command = new GetProdottiVendutiCommand(request, http, model);
					break;
				case "/CompraProdotto":
					command = new CompraProdottoCommand(request, http, model);
					break;
				default:
					command = new NotExistingOperationCommand(request, http, model);
					break;
			}
			
			command.execute();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

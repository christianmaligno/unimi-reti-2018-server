package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import server.commands.AggiungiProdottoCommand;
import server.commands.Command;
import server.commands.GetProdottiDisponibiliCommand;
import server.commands.NotExistingOperationCommand;
import server.io.Http;
import server.io.HttpRequest;
import server.model.Model;
import server.model.ModelFiller;

/**
 * 
 */

public class Server 
{
	private static final int PORT_NUMBER = 80;
	private static final int NTHREADS = 5;
	
	private static final String DB_COMMUNITY_FILENAME = "community.json";
	private static final String DB_PRODUCTS_FILENAME = "prodotti.json";
	
	public static void main(String[] args) throws IOException
	{
		ExecutorService executorService = Executors.newFixedThreadPool(NTHREADS);
		
		Model model = new Model();
		ModelFiller filler = new ModelFiller(model);
		filler.fill(DB_COMMUNITY_FILENAME, DB_PRODUCTS_FILENAME);
		
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		Socket socket;
		while (true)
		{
			socket = serverSocket.accept();
			Worker worker = new Worker(socket, model);
			executorService.execute(worker);
		}
	}
}

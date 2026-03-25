package server.io;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Consente al server di gestire la comunicazione col client tramite l'invio e la ricezione messaggi HTTP e la
 * chiusura di connessione, affidandosi alla connessione col client a livello di trasporto.
 * Sia la richiesta che la risposta HTTP sono rappresentate da due classi chiamate rispettivamente
 * HttpRequest e HttpResponse.
 */
public class Http 
{
	public static final String V1_0 = "HTTP/1.0";
	public static final String GET = "GET";
	public static final String POST = "POST";
	
	private Network network;
	
	public Http(Socket socket) throws IOException
	{
		network = new Network(socket);
	}
	
	/**
	 * Legge riga per riga una richiesta HTTP e
	 * restituisce un oggetto di tipo HttpRequest che conterrà le informazioni contenute nella richiesta.
	 * La lettura della richiesta avviene nei seguenti passi:
	 * 1. Lettura della "request line", contenente, nell'ordine, il metodo, l'url e la versione del protocollo;
	 * 2. Lettura delle righe di header
	 * 3. Lettura di una riga vuota che rappresenta la fine della richiesta
	 * @return HttpRequest	Richiesta letta dal client
	 * @throws IOException
	 */
	public HttpRequest receiveRequest() throws IOException
	{
		String requestLine = network.readLine();
		
		String[] requestLineInfos = requestLine.split(" ");
		
		String method = requestLineInfos[0];
		String url = requestLineInfos[1];
		String httpVersion = requestLineInfos[2];
		
		HttpRequest request = new HttpRequest(method, url);
		
		String line = network.readLine();
		String[] headerInfos;
		while (!line.isEmpty())
		{
			headerInfos = line.split(": ");
			request.addHeader(headerInfos[0], headerInfos[1]);
			line = network.readLine();
		}
		
		return request;
	}
	
	/**
	 * Converte in righe di testo la risposta HTTP fornita tramite parametro e la invia al client.
	 * La conversione avviene nelle seguenti fasi:
	 * 1. Creazione della "status line" contenente, nell'ordine, la versione del protocollo HTTP, lo status code
	 *    e lo status message della risposta;
	 * 2. Creazione delle righe di header
	 * 3. Creazione delle eventuali righe del corpo
	 * @param response	Risposta HTTP da inviare al client
	 */
	public void sendResponse(HttpResponse response)
	{
		ArrayList<String> responseLines = new ArrayList<String>();
		
		String line;
		
		// status line
		line = String.format("%s %d %s", response.getProtocolVersion(), response.getStatusCode(), response.getStatusMessage());
		responseLines.add(line);
		
		String headerValue;
		for (String header : response.getHeaders())
		{
			headerValue = response.getHeaderValue(header);
			line = String.format("%s: %s", header, headerValue);
			responseLines.add(line);
		}
		
		line = "";
		responseLines.add(line);
		
		line = response.getBody();
		if (line != null)
			responseLines.add(line);
		
		sendResponseLines(responseLines);
	}
	
	private void sendResponseLines(ArrayList<String> responseLines)
	{
		for (String line : responseLines)
		{
			network.writeLine(line);
		}
	}
	
	public void closeConnection() throws IOException
	{
		network.closeConnection();
	}
}

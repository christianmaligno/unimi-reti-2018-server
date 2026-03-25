package server.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Fornisce le funzionalità di ricezione e invio di righe di testo col client, nonchè di chiudere la connessione
 * appoggiandosi alla socket di connessione preliminarmente creata col client stesso
 */
public class Network 
{
	private Socket socket;
	private BufferedReader input;
	private PrintWriter output;
	
	public Network(Socket socket) throws IOException
	{
		this.socket = socket;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public String readLine() throws IOException
	{
		return input.readLine();
	}
	
	public void writeLine(String line)
	{
		output.println(line);
	}
	
	public void closeConnection() throws IOException
	{
		socket.close();
	}
}

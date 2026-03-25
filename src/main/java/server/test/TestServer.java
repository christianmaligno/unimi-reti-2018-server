package server.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import server.model.Model;
import server.model.ModelFiller;
import server.model.ProdottiHandler;
import server.model.UtentiHandler;
import server.model.dao.Utente;

public class TestServer 
{
	public static void main(String[] args)
	{
		Gson gson = new Gson();
		int[] arr = {1,4,10,2};
		
		String json = gson.toJson(arr);
		ArrayList<Utente> utenti = new ArrayList<Utente>();
		utenti.add(new Utente("kris-2490", "Christian", "Maligno"));
		utenti.add(new Utente("anto-90", "Antonio", "Meomartino"));
		json = gson.toJson(utenti);
		//System.out.println(json);
		
		/*
		TypeToken<ArrayList<Utente>> tt = new TypeToken<ArrayList<Utente>>(){};
		ArrayList<Utente> list2 = gson.fromJson(json, tt);
		System.out.println(list2);
		LinkedTreeMap o = (LinkedTreeMap) gson.fromJson("{\r\n"
				+ "	\"users\": [\r\n"
				+ "		{\"username\": \"aldorossi90\", \"nome\": \"Aldo\", \"cognome\": \"Rossi\"},\r\n"
				+ "		{\"username\": \"beppeb90\", \"nome\": \"Giuseppe\", \"cognome\": \"Bianchi\"},\r\n"
				+ "		{\"username\": \"almo1998\", \"nome\": \"Alberto\", \"cognome\": \"Moro\"},\r\n"
				+ "		{\"username\": \"pverdi1971\", \"nome\": \"Paolo\", \"cognome\": \"Verdi\"}\r\n"
				+ "	],\r\n"
				+ "	\"friendships\": [\r\n"
				+ "		{\"username1\": \"beppeb90\", \"username2\": \"pverdi1971\"},\r\n"
				+ "		{\"username1\": \"beppeb90\", \"username2\": \"aldorossi90\"}\r\n"
				+ "	] \r\n"
				+ "}", Object.class);
		
		System.out.println(((ArrayList)o.get("users")).get(0).getClass().getCanonicalName());
		
		json = "{\"username\": \"aldorossi90\", \"nome\": \"Aldo\", \"cognome\": \"Rossi\"}";
		Utente p = gson.fromJson(json, Utente.class);
		System.out.println(p.getUsername() + "; " + p.getCognome() + "; " + p.getNome());
		json = "[{\"nome\": \"lampada\", \"prezzo\": \"39\", \"disponibile\": true, \"proprietario\": \"pverdi1971\"}]";
		TypeToken<ArrayList<HashMap>> tt = new TypeToken<ArrayList<HashMap>>(){};
		ArrayList<HashMap> list2 = gson.fromJson(json, tt);
		System.out.println(list2.get(0).keySet());
		*/
		prova2();
	}
	
	private static void prova()
	{
		JsonReader jsonReader;
		Gson gson = new Gson();
		try 
		{
			jsonReader = new JsonReader(new FileReader("community.json"));
			JsonObject jsonObject = JsonParser.parseReader(jsonReader).getAsJsonObject();
			JsonArray jsonArray = jsonObject.getAsJsonArray("users");
			Utente u = gson.fromJson(jsonArray.get(0), Utente.class);
			System.out.println(u.getUsername());
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void prova2()
	{
		Model model = new Model();
		ModelFiller filler = new ModelFiller(model);
		filler.fill("community.json", "prodotti.json");
		
		System.out.println(model);
	}
	
	public static void sendHttpRequest(Socket socket, String resource) throws IOException
	{
		PrintWriter output = new PrintWriter(socket.getOutputStream());
		
		output.println("GET " + output + "HTTP/1.0");
		output.println("user-agent: Mozilla 5.0, Chrome, Safari");
		output.println("host: localhost:6000");
		output.println("Content-type: text/html");
		output.println();
	}
}

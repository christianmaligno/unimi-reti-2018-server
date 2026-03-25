package server.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import server.exceptions.UtenteAlreadyExistsException;
import server.exceptions.UtenteNotExistsException;
import server.model.dao.Prodotto;
import server.model.dao.Utente;

public class ModelFiller 
{
	private Model model;
	
	public ModelFiller(Model model)
	{
		this.model = model;
	}
	
	public void fill(String communityFilename, String prodottiFilename)
	{
		try 
		{
			fillCommunity(communityFilename);
			fillProdotti(prodottiFilename);
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fillCommunity(String communityFilename) throws FileNotFoundException
	{
		Gson gson = new Gson();
		
		JsonReader jsonReader = getJsonReader(communityFilename);
		JsonObject communityObj = JsonParser.parseReader(jsonReader).getAsJsonObject(); 
		
		JsonArray jsonArray = communityObj.getAsJsonArray("users");
		JsonObject jsonObj;
		String username, nome, cognome;
		Utente u;
		for (JsonElement jsonEl : jsonArray)
		{
			jsonObj = jsonEl.getAsJsonObject();
			u = gson.fromJson(jsonObj, Utente.class);
			try {
				model.aggiungiUtente(u.getUsername(), u.getNome(), u.getCognome());
			} catch (UtenteAlreadyExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		jsonArray = communityObj.getAsJsonArray("friendships");
		String username1, username2;
		for (JsonElement jsonEl : jsonArray)
		{
			jsonObj = jsonEl.getAsJsonObject();
			username1 = jsonObj.get("username1").getAsString();
			username2 = jsonObj.get("username2").getAsString();
			try {
				model.aggiungiAmicizia(username1, username2);
			} catch (UtenteNotExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void fillProdotti(String prodottiFilename) throws FileNotFoundException
	{
		ExclusionStrategy prodottoDeserializeStrategy = new ExclusionStrategy() {

			@Override
			public boolean shouldSkipClass(Class<?> classAttribute) {
				// TODO Auto-generated method stub
				return classAttribute.equals(Utente.class);
			}

			@Override
			public boolean shouldSkipField(FieldAttributes fieldAttribute) {
				// TODO Auto-generated method stub
				return fieldAttribute.getDeclaredClass().equals(Utente.class);
			}
			
		};
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder = gsonBuilder.addDeserializationExclusionStrategy(prodottoDeserializeStrategy);
		Gson gson = gsonBuilder.create();
		
		JsonReader jsonReader = getJsonReader(prodottiFilename);
		JsonArray jsonArray = JsonParser.parseReader(jsonReader).getAsJsonArray();
		
		JsonObject  jsonObj;
		Prodotto p;
		String proprietario;
		Utente u;
		for (JsonElement jsonEl : jsonArray)
		{
			jsonObj = jsonEl.getAsJsonObject();
			p = gson.fromJson(jsonObj, Prodotto.class);
			proprietario = jsonObj.get("proprietario").getAsString();
			try {
				model.aggiungiProdotto(p.getNome(), p.getPrezzo(), proprietario);
			} catch (UtenteNotExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private JsonReader getJsonReader(String filename) throws FileNotFoundException
	{
		InputStream in = getClass().getClassLoader().getResourceAsStream(filename);
		return new JsonReader(new InputStreamReader(in));
	}
}

package server.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.google.gson.Gson;

import server.exceptions.OwnProductPurchaseException;
import server.exceptions.ProdottoNotAvailable;
import server.exceptions.ProdottoNotExistsException;
import server.exceptions.UtenteAlreadyExistsException;
import server.exceptions.UtenteNotExistsException;
import server.model.dao.Prodotto;
import server.model.dao.Utente;
import util.StringManipulator;

public class Model 
{
	private UtentiHandler utentiHandler;
	private ProdottiHandler prodottiHandler;
	
	public Model()
	{
		utentiHandler = new UtentiHandler();
		prodottiHandler = new ProdottiHandler();
	}
	
	public synchronized void aggiungiProdotto(String nome, BigDecimal prezzo, String username) throws UtenteNotExistsException
	{
		Utente utente = getUtente(username);
		
		prodottiHandler.aggiungiProdotto(nome, prezzo, utente, true);
	}
	
	public synchronized ArrayList<Prodotto> getProdottiDisponibili(String username) throws UtenteNotExistsException
	{
		Utente utente = getUtente(username);
		
		ArrayList<Prodotto> prodottiDisponibili = prodottiHandler.getProdottiDisponibili();
		
		int i = 0;
		Prodotto p;
		while (i < prodottiDisponibili.size())
		{
			p = prodottiDisponibili.get(i);
			if (utentiHandler.esisteAmicizia(utente.getUsername(), p.getProprietario().getUsername()))
				prodottiDisponibili.remove(i);
			else 
				i++;
		}
		
		return prodottiDisponibili;
	}
	
	public synchronized void compraProdotto(int idProdotto, String username) throws UtenteNotExistsException, ProdottoNotExistsException, ProdottoNotAvailable, OwnProductPurchaseException
	{
		Utente acquirente = getUtente(username);
		Prodotto prodotto = prodottiHandler.getProdotto(idProdotto);
		if (username.equals(prodotto.getProprietario().getUsername()))
			throw new OwnProductPurchaseException();
		if (!prodotto.isDisponibile() || utentiHandler.esisteAmicizia(acquirente.getUsername(), prodotto.getProprietario().getUsername()))
			throw new ProdottoNotAvailable("Prodotto " + idProdotto + " non disponibile");
		
		prodotto.setDisponibile(false);
	}
	
	public synchronized ArrayList<Prodotto> getProdottiVenduti(String username) throws UtenteNotExistsException
	{
		Utente utente = getUtente(username);
		
		return prodottiHandler.getProdottiVenduti(utente.getUsername());
	}
	
	private Utente getUtente(String username) throws UtenteNotExistsException
	{
		Utente utente = utentiHandler.getUtente(username);
		if (utente == null)
			throw new UtenteNotExistsException("Utente " + username + " non esistente");
		
		return utente;
	}

	public synchronized void aggiungiUtente(String username, String nome, String cognome) throws UtenteAlreadyExistsException 
	{
		Utente utente = utentiHandler.getUtente(username);
		if (utente != null)
			throw new UtenteAlreadyExistsException("Utente " + username + " gia' esistente");
		
		utentiHandler.aggiungiUtente(new Utente(username, nome, cognome));
	}

	public void aggiungiAmicizia(String username1, String username2) throws UtenteNotExistsException {
		// TODO Auto-generated method stub
		Utente utente1 = getUtente(username1);
		Utente utente2 = getUtente(username2);
		
		utentiHandler.aggiungiAmicizia(username1, username2);
	}
	
	public String toString()
	{
		String s = "";
		
		return String.format("%s, %s", utentiHandler.toString(), prodottiHandler.toString());
	}
}

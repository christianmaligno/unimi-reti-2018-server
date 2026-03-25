package server.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import server.exceptions.ProdottoNotExistsException;
import server.model.dao.Prodotto;
import server.model.dao.Utente;

public class ProdottiHandler 
{
	private ArrayList<Prodotto> prodotti;
	
	public ProdottiHandler()
	{
		prodotti = new ArrayList<Prodotto>();
	}
	
	public void aggiungiProdotto(Prodotto p)
	{
		aggiungiProdotto(p.getNome(), p.getPrezzo(), p.getProprietario(), p.isDisponibile());
	}
	
	public void aggiungiProdotto(String nome, BigDecimal prezzo, Utente proprietario, boolean disponibile)
	{
		int id = generaId();
		Prodotto p = new Prodotto(id, nome, prezzo, disponibile, proprietario);
		prodotti.add(p);
	}
	
	public ArrayList<Prodotto> getProdottiDisponibili()
	{
		ArrayList<Prodotto> prodottiDisponibili = new ArrayList<Prodotto>();
		
		Prodotto p;
		for (int i = 0; i < prodotti.size(); i++)
		{
			p = prodotti.get(i);
			if (p.isDisponibile())
				prodottiDisponibili.add(p);
		}
		
		return prodottiDisponibili;
	}
	
	public ArrayList<Prodotto> getProdottiVenduti(String username)
	{
		ArrayList<Prodotto> prodottiVenduti = new ArrayList<Prodotto>();
		
		Prodotto p;
		for (int i = 0; i < prodotti.size(); i++)
		{
			p = prodotti.get(i);
			if (p.getProprietario().getUsername().equals(username) && !p.isDisponibile())
			{
				prodottiVenduti.add(p);
			}
		}
		
		return prodottiVenduti;
	}
	
	public Prodotto getProdotto(int idProdotto) throws ProdottoNotExistsException
	{
		Prodotto p;
		for (int i = 0; i < prodotti.size(); i++)
		{
			p = prodotti.get(i);
			if (p.getId() == idProdotto)
				return p;
		}
		
		throw new ProdottoNotExistsException("Prodotto " + idProdotto + " non esistente");
	}
	
	private int generaId()
	{
		return prodotti.size() + 1;
	}
	
	public String toString()
	{
		return String.format("prodotti: %s", prodotti);
	}
}

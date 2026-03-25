package server.model;

import java.util.ArrayList;

import server.model.dao.Utente;

public class UtentiHandler 
{
	private ArrayList<Utente> utenti;
	private ArrayList<ArrayList<Integer>> community;
	
	public UtentiHandler()
	{
		utenti = new ArrayList<Utente>(); 
		community = new ArrayList<ArrayList<Integer>>();
	}
	
	public UtentiHandler(ArrayList<Utente> utenti)
	{
		this.utenti = utenti;
	}
	
	public void aggiungiUtente(Utente u)
	{
		if (getUtente(u.getUsername()) == null)
		{
			utenti.add(u);
			
			for (ArrayList<Integer> riga : community)
			{
				riga.add(0);
			}
			
			ArrayList<Integer> riga = new ArrayList<Integer>();
			for (int i = 0; i < utenti.size(); i++)
			{
				riga.add(0);
			}
			
			community.add(riga);
		}
	}
	
	public Utente getUtente(String username)
	{
		Utente u;
		for (int i = 0; i < utenti.size(); i++)
		{
			u = utenti.get(i);
			if (u.getUsername().equals(username))
				return u;
		}
		
		return null;
	}
	
	public void aggiungiAmicizia(String username1, String username2)
	{
		int index1 = getUtenteIndex(username1);
		int index2 = getUtenteIndex(username2);
		
		community.get(index1).set(index2, 1);
		community.get(index2).set(index1, 1);
	}
	
	public boolean esisteAmicizia(String username1, String username2)
	{
		int index1 = getUtenteIndex(username1);
		int index2 = getUtenteIndex(username2);
		
		return community.get(index1).get(index2) != 0 || community.get(index2).get(index1) != 0;
	}
	
	private int getUtenteIndex(String username)
	{
		Utente u;
		for (int i = 0; i < utenti.size(); i++)
		{
			u = utenti.get(i);
			if (u.getUsername().equals(username))
				return i;
		}
		
		return -1;
	}
	
	public String toString()
	{
		String s;
		
		String listaAmicizie = "";
		for (int i = 0; i < utenti.size(); i++)
		{
			for (int j = 0; j < utenti.size(); j++)
			{
				if (community.get(i).get(j) != 0)
				{
					if (!listaAmicizie.isEmpty())
						listaAmicizie += ",";
					listaAmicizie += String.format("{\"%s\",\"%s\"}", utenti.get(i).getUsername(),
							utenti.get(j).getUsername());
				}
			}
		}
		String amicizie = "[" + listaAmicizie + "]";
		
		return String.format("utenti: %s, amicizie: %s", utenti, amicizie);
	}
}

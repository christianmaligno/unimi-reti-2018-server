package server.model.dao;

import java.util.ArrayList;

public class Utente 
{
	private String username;
	private String nome;
	private String cognome;
	
	public Utente(String username, String nome, String cognome)
	{
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	
	public String toString()
	{
		return String.format("{username: %s, nome: %s, cognome: %s}", username, nome, cognome);
	}
}

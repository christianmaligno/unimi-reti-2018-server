package server.model.dao;

import java.math.BigDecimal;

public class Prodotto 
{
	private int id;
	private String nome;
	private BigDecimal prezzo;
	private boolean disponibile;
	private Utente proprietario;
	
	public Prodotto(int id, String nome, BigDecimal prezzo, boolean disponibile, Utente proprietario)
	{
		this.id = id;
		this.nome = nome;
		this.prezzo = prezzo;
		this.disponibile = disponibile;
		this.proprietario = proprietario;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public BigDecimal getPrezzo()
	{
		return prezzo;
	}
	
	public void setDisponibile(boolean disponibile)
	{
		this.disponibile = disponibile;
	}
	
	public boolean isDisponibile()
	{
		return disponibile;
	}
	
	public Utente getProprietario()
	{
		return proprietario;
	}

	public void setProprietario(Utente proprietario) 
	{
		this.proprietario = proprietario;
	}
	
	public String toString()
	{
		return String.format("{id: %d, nome: %s, prezzo: %s, disponibile: %b, proprietario: %s}", 
				id, nome, prezzo, disponibile, proprietario.getUsername());
	}
}

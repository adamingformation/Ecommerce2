package fr.adaming.dao;



import fr.adaming.model.Client;


public interface IClientDao {

public Client addClient(Client cl);
	
	public void deleteClient(long idCl);
	
	public Client updateClient(Client cl);
	
	public Client getClientByNomEmail(String nom, String email);
}

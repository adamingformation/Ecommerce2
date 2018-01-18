package fr.adaming.service;



import fr.adaming.model.Client;


public interface IClientService {

public Client addClient(Client cl);
	
	public void deleteClient(long idCl);
	
	public Client updateClient(Client cl);
	
	public Client getClientByNomEmail(String nom, String email);
}

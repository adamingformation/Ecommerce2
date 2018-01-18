package fr.adaming.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Client;

@Service("clService")
@Transactional
public class ClientServiceImpl implements IClientService{

	@Autowired
	private IClientDao clientDao;

	//setters
	public void setClientDao(IClientDao clientDao) {
		this.clientDao = clientDao;
	}

	//Methodes
	@Override
	public Client addClient(Client cl) {
		return clientDao.addClient(cl);
	}

	@Override
	public void deleteClient(long idCl) {
		clientDao.deleteClient(idCl);
		
	}

	@Override
	public Client updateClient(Client cl) {
		return clientDao.updateClient(cl);
	}

	@Override
	public Client getClientByNomEmail(String nom, String email) {
		return clientDao.getClientByNomEmail(nom, email);
	}
	
	
}

package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Service("comService")
@Transactional
public class CommandeServiceImpl implements ICommandeService{

	@Autowired
	private ICommandeDao commandeDao;

	//Setters
	public void setCommandeDao(ICommandeDao commandeDao) {
		this.commandeDao = commandeDao;
	}

	//Methodes
	@Override
	public Commande addCommande(Commande c) {
		
		Commande cOut = commandeDao.addCommande(c);
		return cOut;
	}

	@Override
	public Commande updateCommande(Commande c) {
		Commande cOut = commandeDao.updateCommande(c);
		return cOut;
	}

	@Override
	public void deleteCommande(long idCommande) {
		commandeDao.deleteCommande(idCommande);
		
	}


	@Override
	public Commande getCommande(long idCommande) {
		Commande cOut = commandeDao.getCommande(idCommande);
		return cOut;
	}

	@Override
	public List<Commande> gettAllCommande(long idCl) {
		List<Commande> listeco = commandeDao.gettAllCommande(idCl);
		return listeco;
	}
	
	@Override
	public Commande getCommandeByIdClNULL(long idCl) {
		Commande cOut = commandeDao.getCommandeByIdClNULL(idCl);
		return cOut;
	}
	
	
}

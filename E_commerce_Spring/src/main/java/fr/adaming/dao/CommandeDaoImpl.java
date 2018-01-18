package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Commande;

@Repository
public class CommandeDaoImpl implements ICommandeDao {

	//injection du collaborateur sf
	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	// méthode
	@Override
	public Commande addCommande(Commande c) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		s.save(c);
		return c;
	}

	@Override
	public Commande updateCommande(Commande c) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		Commande c1=(Commande) s.get(Commande.class, c.getIdCommande());
		c1.setDateCommande(c1.getDateCommande());
		c1.setClient(c1.getClient());
		s.saveOrUpdate(c1);
		return c1;
	}

	@Override
	public void deleteCommande(long idCommande) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		Commande cOut = (Commande) s.get(Commande.class, idCommande);
		s.delete(cOut);

	}

	@Override
	public List<Commande> gettAllCommande(long idCl) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// construre la requête
		String req = "SELECT c FROM Commande AS c WHERE c.client.idClient=:PidCl";

		// création du query
		Query query = s.createQuery(req);

		// Spécification des paramètres
		query.setParameter("PidCl", idCl);

		// création de la nouvelle liste des lignes commandes
		List<Commande> listeCommande = query.list();

		return listeCommande;
	}

	@Override
	public Commande getCommande(long idCommande) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		return (Commande) s.get(Commande.class, idCommande);
	}
	
	@Override
	public Commande getCommandeByIdClNULL(long idCl) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// construre la requête
		String req = "SELECT c FROM Commande AS c WHERE c.client.idClient IS NULL";

		// création du query
		Query query = s.createQuery(req);

		// création de la nouvelle liste des lignes commandes
		Commande cOut = (Commande) query.uniqueResult();

		return cOut;
	}

}

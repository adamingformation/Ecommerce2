package fr.adaming.dao;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository
public class ClientDaoImpl implements IClientDao {

	//injection du collaborateur sf
	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

		//méthodes
		@Override
		public Client addClient(Client cl) {
			//recup session hibernate
			Session s = sf.getCurrentSession();
			s.save(cl);
			return cl;
		}

		@Override
		public void deleteClient(long idCl) {
			//recup session hibernate
			Session s = sf.getCurrentSession();
			Client clOut = (Client) s.get(Client.class, idCl);
			s.delete(clOut);
			
		}

		@Override
		public Client updateClient(Client cl) {
			//recup session hibernate
			Session s = sf.getCurrentSession();
			Client c1=(Client) s.get(Client.class, cl.getIdClient());
			c1.setNomClient(c1.getNomClient());
			c1.setAdresse(c1.getAdresse());
			c1.setEmail(c1.getEmail());
			c1.setTel(c1.getTel());
			s.saveOrUpdate(c1);;
			return c1;
		}

		@Override
		public Client getClientByNomEmail(String nom, String email) {
			//recup session hibernate
			Session s = sf.getCurrentSession();
			// création de la requete
			String req = "SELECT cl FROM Client AS cl WHERE cl.nomClient=:pNomCl AND cl.email=:pEmailCl";

			// création du query
			Query query = s.createQuery(req);

			// Spécification des paramètres
			query.setParameter("pNomCl", nom);
			query.setParameter("pEmailCl", email);

			// obtention du client en question
			Client clOut = (Client) query.uniqueResult();
			return clOut;
		}
		
		
		
		
		
}

package fr.adaming.dao;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Admin;

@Repository
public class AdminDaoImpl implements IAdminDao{


	//injection du collaborateur sf
		@Autowired
		private SessionFactory sf;
		
		//setter pour l'injection de dependance	
		public void setSf(SessionFactory sf) {
			this.sf = sf;
		}
	
	

	@Override
	public Admin isExist(Admin a) throws Exception {
		//recup session hibernate
				Session s = sf.getCurrentSession();
				
		// construire la requete JPQL
		String req= "SELECT a FROM Admin a WHERE a.mail=:pMail AND a.mdp=:pMdp";
		
		//crée un query
		Query query=s.createQuery(req);
		
		//passage des params
		query.setParameter("pMail", a.getMail());
		query.setParameter("pMdp", a.getMdp());
		
		//envoyer la requete et recup l'agent
		Admin aOut= (Admin) query.uniqueResult();
		
		return aOut;
	}

}

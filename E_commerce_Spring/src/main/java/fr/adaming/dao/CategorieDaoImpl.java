package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao{
	
	//injection du collaborateur sf
	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

		

	@Override
	public List<Categorie> getAllCategorie() {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// construire la requete JPQL
		String req = "SELECT c FROM Categorie as c";

		// creer la query
		Query query = s.createQuery(req);

		// envoyer la requete et recup resultat

		return query.list();
	}

	@Override
	public Categorie getCategorieById(long id) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		
		Categorie c=(Categorie) s.get(Categorie.class,id);

				 return c;
	}

	@Override
	public Categorie addCategorie(Categorie C) {
		//recup session hibernate
				Session s = sf.getCurrentSession();
				
		s.save(C);

		// 1er C sans id mais retour aura un id
		return C;
	}

	@Override
	public long deleteCategorie(long id) {
		//recup session hibernate
			Session s = sf.getCurrentSession();
		 Categorie c=(Categorie) s.get(Categorie.class,id);
		 s.delete(c);
		 if(c==null){
			 return  1; 
		 }else{
			 return  0;
		 }
		
	}

	@Override
	public Categorie updateCategorie(Categorie C) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		Categorie c1= (Categorie) s.get(Categorie.class, C.getIdCategorie());
		c1.setDescription(c1.getDescription());
		c1.setNomCategorie(c1.getNomCategorie());
		c1.setPhoto(c1.getPhoto());
		s.saveOrUpdate(c1);
		return c1;
	}
	
	
	
	

}

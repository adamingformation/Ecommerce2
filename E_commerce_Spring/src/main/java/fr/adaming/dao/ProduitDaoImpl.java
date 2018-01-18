package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.model.Produit;

@Service("pService")
@Transactional
public class ProduitDaoImpl implements IProduitDao {

	//injection du collaborateur sf
	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}




	@Override
	public Produit addProduitStock(Produit p) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		s.persist(p);
		return p;
	}

	@Override
	public List<Produit> getAllProduit() {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// requete JPQL
		String req = "SELECT p FROM Produit as p ";

		// creer query
		Query query = s.createQuery(req);

		// envoyer la req et recup la liste
		List<Produit> liste = query.list();

		return liste;

	}

	@Override
	public Produit getProduitById(long id) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// requete JPQL
		String req = "SELECT p FROM Produit as p WHERE p.id=:pId";

		// creer query
		Query query = s.createQuery(req);

		query.setParameter("pId", id);

		try {
			// envoyer et recup la liste
			Produit p = (Produit) query.uniqueResult();
			return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



	public int deleteProduitStock(long id) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		Produit p=(Produit) s.get(Produit.class, id);
		s.delete(p);
		return 1;

	}

	@Override
	public Produit updateProduit(Produit p) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		Produit p1=(Produit) s.get(Produit.class, p.getIdProduit());
		p1.setDescription(p1.getDescription());
		
		s.saveOrUpdate(p1);
		return p1;
	}
	
	@Override
	public List<Produit> getAllPorduitByCategorie(int id_c) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		// Creation de la requete JPQL
		String req = "select p from Produit as p where categorie_id=:pcId";
		System.out.println("ID categorie =" +id_c);
		// Creer un query
		Query query = s.createQuery(req);
		
		// passage des param
		query.setParameter("pcId", id_c);

		// Envoyer la requete
		return query.list();
	}

}

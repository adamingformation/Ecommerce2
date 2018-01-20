package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	
	//injection du collaborateur sf
	@Autowired
	private SessionFactory sf;
	
	//setter pour l'injection de dependance	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}




	@Override
	public LigneCommande addLCommande(LigneCommande LC) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		s.save(LC);
		return LC;
	}



	@Override
	public void deleteLCommande(long idLC) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		LigneCommande lcOut=(LigneCommande) s.get(LigneCommande.class, idLC);
		s.delete(lcOut);
	}
	@Override
	public List<LigneCommande> getAllLCommande(){
		//recup session hibernate
				Session s = sf.getCurrentSession();
				// construire la requete JPQL
				String req = "SELECT lc FROM LigneCommande as lc WHERE lc.commande IS NULL";

				// creer la query
				Query query = s.createQuery(req);
				System.out.println("query : " + query);
				// mettre les parametres
				System.out.println("liste avant evoyer liste : " + query.list());
				// création de la nouvelle liste des lignes commandes
				List<LigneCommande> listeLCommande = query.list();
				System.out.println("liste : " + listeLCommande);

				return listeLCommande;
			}


	@Override
	public LigneCommande updateLCommande(LigneCommande LC) {
		//recup session hibernate
		Session s = sf.getCurrentSession();
		LigneCommande lc=(LigneCommande) s.get(LigneCommande.class, LC.getIdNumLigne());
		
		lc.setCommande(lc.getCommande());
		lc.setPanier(lc.getPanier());
		lc.setPrix(lc.getPrix());
		lc.setProduit(lc.getProduit());
		lc.setQuantite(lc.getQuantite());
		
		
		s.saveOrUpdate(lc);
		return lc;
		
		//requete HQL
//		String req="UPDATE LigneCommande lc set lc."
	}

	
	public LigneCommande getLCommandeById(long idLC){
		//recup session hibernate
		Session s = sf.getCurrentSession();
		LigneCommande lcOut=(LigneCommande) s.get(LigneCommande.class, idLC);
		return lcOut;
	}
	
	public List<LigneCommande> getAllLCommandeByIdCommande(long idCommande){
		//recup session hibernate
		Session s = sf.getCurrentSession();
		//construire la requete
		String req="SELECT lc FROM LigneCommande AS lc WHERE lc.commande.idCommande=:pidC";
		
		//creation query 
		Query query=s.createQuery(req);
		
		//Spe des param
		query.setParameter("pidC", idCommande);
		
		//creation de la nouvelle liste des lignes commandes
		List<LigneCommande> listeLCommande=query.list();
		
		return listeLCommande;
	}



	@Override
	public double calculPrixLigneCommande(LigneCommande lc, Produit p) {

		System.out.println("lc :" + lc + "\n" + "p : " + p);
		System.out.println("p.getprix : " + p.getPrix());
		double prixTotal = p.getPrix() * lc.getQuantite();
		System.out.println("prix :" + prixTotal);
		return prixTotal;
	}
}

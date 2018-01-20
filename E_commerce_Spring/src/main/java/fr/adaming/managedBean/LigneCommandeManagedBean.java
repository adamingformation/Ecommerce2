package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Admin;
import fr.adaming.model.Categorie;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "lcMB")
@RequestScoped
public class LigneCommandeManagedBean implements Serializable {

	// asso uml en java
	@ManagedProperty(value = "#{ligneCoService}")
	private ILigneCommandeService lcService;
	@ManagedProperty(value="#{pdtService}")
	private IProduitService produitService;
	@ManagedProperty(value="#{comService}")
	private ICommandeService commandeService;

	// Attributs
	private Produit produit;
	private Commande commande;
	private LigneCommande lcommande;
	private Admin admin;
	private HttpSession maSession;
	private List<LigneCommande> listeLigneCommande;
	private int idCommande;
	private boolean indice = false;

	public LigneCommandeManagedBean() {
		this.lcommande = new LigneCommande();
		this.produit = new Produit();
		this.listeLigneCommande = new ArrayList<LigneCommande>();
	}
	
	
	public boolean isIndice() {
		return indice;
	}


	public void setIndice(boolean indice) {
		this.indice = indice;
	}


	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public int getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}

	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	public LigneCommande getLcommande() {
		return lcommande;
	}

	public void setLcommande(LigneCommande lcommande) {
		this.lcommande = lcommande;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}
	

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}


	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}


	public String ajouterLigneCommande() {
		// récupération du produit par l'id entré
		this.produit = produitService.getProduitById(this.produit.getIdProduit());
		System.out.println("************recup produit :" +this.produit);
		// spécification du produit pour la ligne de commande
		this.lcommande.setProduit(this.produit);
		// calcul du prix total
		this.lcommande.setPrix(lcService.calculPrixLigneCommande(this.lcommande, this.produit));

		if (this.produit.getQuantite() >= 0) {
			// modification de la quantité de produit en stock
			int quantiteRestante = this.produit.getQuantite() - this.lcommande.getQuantite();

			// Modifier la quantité de produit en stock restant
			if (quantiteRestante > 0) {
				this.produit.setQuantite(quantiteRestante);
				produitService.updateProduit(this.produit);

				// ajout de la ligne dans la base de données
				this.lcommande = lcService.addLCommande(this.lcommande);
				System.out.println(this.lcommande);
			}

		}
		// pour envoyer les lignes commandes dans le panier
		// récupérer toutes les lignes de commandes avec un id commande null
		// (car non validée)
		this.listeLigneCommande = lcService.getAllLCommande();

		// Passer la liste des lignes commandes dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listeLCPanier",
				this.listeLigneCommande);

		if (this.lcommande.getIdNumLigne() != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la ligne de commande est  ajoutée"));
			return "produit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("l'ajout de la ligne de commande a échouée"));
			return "produit";
		}

	}

	public String deleteLCommande() {

		// pas de retour avec un void sinon comme autre en modifiant en int
		lcService.deleteLCommande(this.lcommande.getIdNumLigne());
		LigneCommande lcOut = lcService.getLCommandeById(this.lcommande.getIdNumLigne());
		if (lcOut == null) {
			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la supression n'a pas marché"));
			return "supLCommande";
		}

	}

	public String updateLCommande() {
		// récupération du produit par id choisi
		this.produit = produitService.getProduitById(this.produit.getIdProduit());
		// spécification du produit pour la ligne de commande
		this.lcommande.setProduit(this.produit);
		// calcul du prix total
		this.lcommande.setPrix(lcService.calculPrixLigneCommande(this.lcommande, this.produit));
		// update de la ligne dans la base de données
		this.lcommande = lcService.updateLCommande(this.lcommande);
		if (this.lcommande != null) {
			return "accueil";
		} else {
			return "modifierLCommande";
		}
	}

	public String rechercheLCommande() {
		LigneCommande verif = lcService.getLCommandeById(this.lcommande.getIdNumLigne());

		if (verif != null) {

			this.lcommande = verif;

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la recherche n'a pas marché"));

		}
		return "rechercheLCommande";
	}
	public String afficherLigneCommandeByIDCommande() {

		indice = true;
		
		this.listeLigneCommande = lcService.getAllLCommandeByIdCommande(this.idCommande);
		for (LigneCommande ligneCommande : this.listeLigneCommande) {
			System.out.println(ligneCommande);
		}

		this.commande = commandeService.getCommande(this.idCommande);
		return "accueilClient";
	}
}

package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="lignecommandes")
public class LigneCommande implements Serializable {

	//attributs
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idNumLigne;
	private int quantite;
	private int prix;

	
	
	//transformation uml en java
	@ManyToOne
	@JoinColumn(name="produit_id",referencedColumnName="idProduit")
	private Produit produit;
	
	@ManyToOne
	@JoinColumn(name="commande_id",referencedColumnName="idCommande")
	private Commande commande;
	
	@ManyToOne
	@JoinColumn(name="id_panier" ,referencedColumnName="idPanier")
	private Panier panier;

	
	//3 constructeurs
	public LigneCommande() {
		super();
	}
	public LigneCommande(int quantite, int prix) {
		super();
		this.quantite = quantite;
		this.prix = prix;
	}
	
	
	
	public LigneCommande(Long idNumLigne, int quantite, int prix) {
		super();
		this.idNumLigne = idNumLigne;
		this.quantite = quantite;
		this.prix = prix;
	}
	//getters et setters
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}
	public Long getIdNumLigne() {
		return idNumLigne;
	}
	public void setIdNumLigne(Long idNumLigne) {
		this.idNumLigne = idNumLigne;
	}
	
	

	public Panier getPanier() {
		return panier;
	}
	public void setPanier(Panier panier) {
		this.panier = panier;
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
	@Override
	public String toString() {
		return "LigneCommande [idNumLigne=" + idNumLigne + ", quantite=" + quantite + ", prix=" + prix + "]";
	}
	
	
}

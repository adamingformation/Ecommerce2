package fr.adaming.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "produits")

public class Produit implements Serializable {

	// attribut
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProduit;

	private String designation;
	private String description;
	private double prix;
	private int quantite;
	private boolean selectionne = false;
	@Lob
	private byte[] photo;
	@Transient
	private String image;
	
	

	// transformation relation uml en java
	@ManyToOne
	@JoinColumn(name = "categorie_id", referencedColumnName = "idCategorie")
	private Categorie categorie;

	@OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
	private List<LigneCommande> listeLigne;

	// constructeur
	public Produit() {
		super();
	}

	public Produit(String designation, String description, double prix, int quantite, boolean selectionne,
			byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	public Produit(long idProduit, String designation, String description, double prix, int quantite,
			boolean selectionne, byte[] photo) {
		super();
		this.idProduit = idProduit;
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.selectionne = selectionne;
		this.photo = photo;
	}

	public Produit(String designation, String description, double prix, int quantite, byte[] photo) {
		super();
		this.designation = designation;
		this.description = description;
		this.prix = prix;
		this.quantite = quantite;
		this.photo = photo;
	}

	


	// getter et setter
	public long getIdProduit() {
		return idProduit;
	}

	public void setIdProduit(long idProduit) {
		this.idProduit = idProduit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	
	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public List<LigneCommande> getListeLigne() {
		return listeLigne;
	}

	public void setListeLigne(List<LigneCommande> listeLigne) {
		this.listeLigne = listeLigne;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	


	@Override
	public String toString() {
		return "Produit [idProduit=" + idProduit + ", designation=" + designation + ", description=" + description
				+ ", prix=" + prix + ", quantite=" + quantite + ", selectionne=" + selectionne + ", photo="
				+ Arrays.toString(photo) + "]";
	}

}

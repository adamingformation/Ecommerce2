package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="paniers")
public class Panier implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPanier;

	//transformation uml en java
	@OneToMany(mappedBy="panier")
	private List<LigneCommande> listeLigneCommande;

	public Panier() {
		super();
	}

	public Panier(List<LigneCommande> listeLigne) {
		super();
		this.listeLigneCommande = listeLigne;
	}

	public List<LigneCommande> getListeLigne() {
		return listeLigneCommande;
	}

	public void setListeLigne(List<LigneCommande> listeLigne) {
		this.listeLigneCommande = listeLigne;
	}
	
	

}

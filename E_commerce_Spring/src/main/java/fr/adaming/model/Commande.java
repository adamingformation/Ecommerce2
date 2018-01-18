package fr.adaming.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="commandes")

public class Commande implements Serializable{
	
	//attribut
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long idCommande;
	
	@Temporal(TemporalType.DATE)
	private Date dateCommande= new Date();
	
	//transformation uml en java
	@OneToMany
	@JoinColumn(referencedColumnName="idCommande")
	private List<LigneCommande> listeligne;
	
	
	@ManyToOne
	@JoinColumn(name="client_id",referencedColumnName="idCLient")
	private Client client;
	
	//constructeur
	public Commande() {
		super();
	}


	public Commande(Date dateCommande) {
		super();
		this.dateCommande = dateCommande;
	}


	public Commande(long idCommande, Date dateCommande) {
		super();
		this.idCommande = idCommande;
		this.dateCommande = dateCommande;
	}

	//getter et setter
	public long getIdCommande() {
		return idCommande;
	}


	public void setIdCommande(long idCommande) {
		this.idCommande = idCommande;
	}


	public Date getDateCommande() {
		return dateCommande;
	}


	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	

	public List<LigneCommande> getListeligne() {
		return listeligne;
	}


	public void setListeligne(List<LigneCommande> listeligne) {
		this.listeligne = listeligne;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}
	
	

	@Override
	public String toString() {
		return "Commande [idCommande=" + idCommande + ", dateCommande=" + dateCommande + "]";
	}
	
	
	
	
	

}

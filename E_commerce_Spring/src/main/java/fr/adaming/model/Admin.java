package fr.adaming.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="admin")
public class Admin implements Serializable {

	//declaration des attributs
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_a")
	private int id;
	private String mail,mdp;


	
	//3 constructeurs
	public Admin() {
		super();
	}


	public Admin(String mail, String mdp) {
		super();
		this.mail = mail;
		this.mdp = mdp;
	}


	public Admin(int id, String mail, String mdp) {
		super();
		this.id = id;
		this.mail = mail;
		this.mdp = mdp;
	}


	//getters et setters
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	public String getMdp() {
		return mdp;
	}


	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	
	
}

package fr.adaming.managedBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import fr.adaming.model.Admin;
import fr.adaming.model.Categorie;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;
import fr.adaming.service.IAdminServive;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "aMB")
@RequestScoped
public class AdminManagedBean implements Serializable {

	@ManagedProperty(value = "#{aService}")
	private IAdminServive adminService;
	private Admin admin;

	@ManagedProperty(value = "#{catService}")
	private ICategorieService cService;
	private List<Categorie> listeCategories;

	@ManagedProperty(value = "#{pdtService}")
	private IProduitService pService;
	private List<Produit> listeProduit;
	
	@ManagedProperty(value = "#{ligneCoService}")
	private ILigneCommandeService lcService;
	private List<LigneCommande> listeLCommande;
	
	private HttpSession maSession;
	
	
	
	public void setAdminService(IAdminServive adminService) {
		this.adminService = adminService;
	}

	public void setcService(ICategorieService cService) {
		this.cService = cService;
	}

	public void setpService(IProduitService pService) {
		this.pService = pService;
	}

	public void setLcService(ILigneCommandeService lcService) {
		this.lcService = lcService;
	}

	public AdminManagedBean() {
		this.admin = new Admin();
	}
	
	
	// methode qui s'execute apres l'instanviation du managedBean
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);		
	}
	
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String seConnecter() {
		try {
			Admin aOut = adminService.isExist(this.admin);

			// recup liste catégorie et produit et ligne commande
			this.getAllCategorie();
			this.getAllProduit();
			listeLCommande=lcService.getAllLCommande();
			
			// ajouter les listes deans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList",listeCategories);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitList", listeProduit);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("LCommandeList",listeLCommande);

			// ajouter l'agent dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adminSession", aOut);
			
			//maSession.setAttribute("produitListe", this.listeProduit);
			
			
			return "gestionStock";

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Admin n'existe pas"));
		}
		return "login";
	}
	public void getAllCategorie() {

		List<Categorie> listOut = cService.getAllCategorie();
		this.listeCategories = new ArrayList<Categorie>();

		for (Categorie element : listOut) {
			if (element.getPhoto() == null) {
				
				element.setImage(null);

			} else {

				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}

			this.listeCategories.add(element);
		}

	}
	
	public void getAllProduit() {

		List<Produit> listOut = pService.getAllProduit() ;
		this.listeProduit = new ArrayList<Produit>();

		for (Produit element : listOut) {
			if (element.getPhoto() == null) {
				
				element.setImage(null);

			} else {

				element.setImage("data:image/png;base64," + Base64.encodeBase64String(element.getPhoto()));
			}

			this.listeProduit.add(element);
		}

	}
	

}

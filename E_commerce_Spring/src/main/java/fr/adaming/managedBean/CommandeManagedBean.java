package fr.adaming.managedBean;

import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.model.Categorie;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;

@ManagedBean(name = "comMB")
@RequestScoped
public class CommandeManagedBean implements Serializable{

	@ManagedProperty(value = "#{comService}")
	ICommandeService commandeService;
	@ManagedProperty(value = "#{ligneCoService}")
	ILigneCommandeService ligneCommandeService;
	
//attributs
	private Commande commande;
	private Client client;
	private List<LigneCommande> listeLCo;
	private LigneCommande ligneCommande;
	private long idCommande;
	
	//Constructeurs
	public CommandeManagedBean() {
		this.commande = new Commande();
		this.client = new Client();
		this.ligneCommande = new LigneCommande();
	}

	//Getters et Setters
	
	
	public Commande getCommande() {
		return commande;
	}

	public long getIdCommande() {
		return idCommande;
	}

	public void setIdCommande(long idCommande) {
		this.idCommande = idCommande;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<LigneCommande> getListeLCo() {
		return listeLCo;
	}

	public void setListeLCo(List<LigneCommande> listeLCo) {
		this.listeLCo = listeLCo;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}
	
	//les methodes
	public String ajouterCommande(){
		//Cr�er une commande
		this.commande = commandeService.addCommande(this.commande);
		
		//r�cup�rer ligne co qui ont un id null
		this.listeLCo = ligneCommandeService.getAllLCommande();
		System.out.println("-----------------liste lc : " + listeLCo);
		//Donne id de la commande a chaque ligne de co
		for (LigneCommande LC : this.listeLCo) {
			LC.setCommande(this.commande);
			this.ligneCommande = ligneCommandeService.updateLCommande(LC);
			System.out.println("-----commande de la LC : " + this.ligneCommande);
		}

		//g�n�rer une nouvelle liste des ligne commande qui sont associ�es � la commande
		this.listeLCo = ligneCommandeService.getAllLCommandeByIdCommande(this.commande.getIdCommande());
		System.out.println("liste des LC par id  de la commande : " + this.listeLCo);
		
		//On ajoute dans la session l'id de la commande pour l'avoir lors de l'affichage dans l'espace client(accueil)
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idCommande", this.commande.getIdCommande());
		if (this.commande != null) {
			return "loginClient";
		} else {
			return "panier";
		}
	}
	public String supprimerCommande(){
		commandeService.deleteCommande(this.commande.getIdCommande());
		Commande cOut = commandeService.getCommande(this.commande.getIdCommande());

		if (cOut == null) {
			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la suppression de la commande a �chou�e"));
			return "supprimerCommande";
		}
	}
	
	public String modifierCommande(){
		Commande c=commandeService.updateCommande(this.commande);
		if(c!=null){
			return "accueil";
			
		}else{
			return "modifierCommande";
		}
	}
	
	public String rechercherCommandeParIdClientNull() {
		Commande cOut = commandeService.getCommandeByIdClNULL(this.client.getIdClient());
		if (cOut != null) {
			this.commande = cOut;
			return "rechercheCommande";
		} else {
			return "rechercherCommande";
		}
	}
public String rechercherCommandeIDC(){
		
		this.commande=commandeService.getCommande(this.commande.getIdCommande());
		
		return "rechercherCommande";
		
	}

public String envoyerFacture() {
	//r�cup�ration du client correspondant 
	
	this.listeLCo = ligneCommandeService.getAllLCommandeByIdCommande(this.idCommande);

	// R�cupere la commande
	this.commande = commandeService.getCommande(this.idCommande);

	// Cr�ation d'un document de taille A4 avec une marge de 36 sur
	// chaque bord
	// Document document = new Document(PageSize.A4, 36, 36, 36, 36);
	Document document = new Document();
	try {
		// D�finir le type de document souhait� ainsi que son nom
		PdfWriter.getInstance(document, new FileOutputStream("C:/Users/marin/Desktop/PDFTp/commande" + this.idCommande + ".pdf"));
		// Ouverture du document
		document.open();
		// Definition des polices
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16);

		// Cr�ation des elements � ajouter dans le document
		String titre = "Facture pour la commande " + this.idCommande + " de "
				+ this.commande.getClient().getNomClient() + " du " + this.commande.getDateCommande() + "\n";
		Chunk c1 = new Chunk(titre, chapterFont);

		Phrase p1 = new Phrase("Recapitulatif de votre commande : \n\n\n\n");
		
		PdfPTable table = new PdfPTable(6);
		table.addCell("ID Ligne Commande");
		table.addCell("Id Produit");
		table.addCell("Produit");
		table.addCell("Prix unitaire");
		table.addCell("Quantit�");
		table.addCell("Prix total");
		double prixT=0;
		for (LigneCommande ligneCommande : this.listeLCo) {
			table.addCell(Long.toString(ligneCommande.getIdNumLigne()));
			table.addCell(Long.toString(ligneCommande.getProduit().getIdProduit()));
			table.addCell(ligneCommande.getProduit().getDesignation());
			table.addCell(Double.toString(ligneCommande.getProduit().getPrix()));
			table.addCell(Integer.toString(ligneCommande.getQuantite()));
			table.addCell(Double.toString(ligneCommande.getPrix()));
			prixT=prixT+ligneCommande.getPrix();
		}
		
		Phrase p2 = new Phrase("Total de la commande : " +prixT +"�");
		
		//Ajout des elements dans le documents
		document.add(new Paragraph(c1));
	
		document.add(new Paragraph(p1));

		
		document.add(table);
		
		
		document.add(new Paragraph(p2));
		
		
		
	} catch (Exception e) {
		// F
		System.out.println("Echec envoyer mail");
		e.printStackTrace();
	}
	// Fermeture du document
	document.close();

	try{
	 // Creation de la piece jointe
	 EmailAttachment attachment = new EmailAttachment();
	 attachment.setPath("C:/Users/marin/Desktop/PDFTp/commande" + this.idCommande + ".pdf");
	 attachment.setDisposition(EmailAttachment.ATTACHMENT);
	 
	 
	 // Creation du mail avec piece jointe
	 MultiPartEmail email = new MultiPartEmail();
	 email.setHostName("smtp.googlemail.com");
	 email.setSmtpPort(465);
	 // Parametrage du compte
	 email.setAuthenticator(new DefaultAuthenticator("myentreprise44000@gmail.com",
	 "adaming44"));
	 email.setSSLOnConnect(true);
	 // Adresse de l'envoyeur
	 email.setFrom("getAllLCommandeByIdCommande@gmail.com");
	// Objet du mail
	 email.setSubject("Votre commande " +this.idCommande);
	 //Corps du mail
	 email.setMsg("Bonjour, \n \n Merci pour votre commande, veuillez trouver ci-joint le recapitulatif \n");
	 //destinataire du mail
	 email.addTo(this.commande.getClient().getEmail());
	
	 // Ajouter la pi�ce jointe
	 email.attach(attachment);
	 // envoyer le mail
	 email.send();
	} catch (EmailException em){
		em.printStackTrace();
	}
	return "produit";
	
	
}
}

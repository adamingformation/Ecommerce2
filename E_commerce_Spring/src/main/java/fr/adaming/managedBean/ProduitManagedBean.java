package fr.adaming.managedBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import fr.adaming.model.Admin;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "pMB")
@ViewScoped

public class ProduitManagedBean implements Serializable {

	@ManagedProperty(value = "#{pdtService}")
	private IProduitService produitService;

	@ManagedProperty(value = "#{catService}")
	private ICategorieService categorieService;

	private List<Produit> listeProduit;
	private List<Produit> listeProduitAll;
	private Produit produit;
	private int nbreProduit;
	private Categorie categorie;
	private int idCategorie;
	private List<Categorie> listeCategories;
	private HttpSession maSession;

	private String image;

	// chemin export du pdf
	// public static final String chemin =
	// "C://Users//Adaming//Desktop//PDF//pdfproduit.pdf";

	public ProduitManagedBean() {
		this.produit = new Produit();
		this.categorie = new Categorie();
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	// methode qui s'execute apres l'instanviation du managedBean
	@PostConstruct
	public void init() {
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categoriesList", listeCategories);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitList", listeProduit);
	}

	public List<Produit> getListeProduitAll() {
		return listeProduitAll;
	}

	public void setListeProduitAll(List<Produit> listeProduitAll) {
		this.listeProduitAll = listeProduitAll;
	}

	public int getNbreProduit() {
		return nbreProduit;
	}

	public void setNbreProduit(int nbreProduit) {
		this.nbreProduit = nbreProduit;
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategories;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategories = listeCategorie;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public IProduitService getProduitService() {
		return produitService;
	}

	public ICategorieService getCategorieService() {
		return categorieService;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public String rechercherProduit() {
		this.produit = produitService.getProduitById(this.produit.getIdProduit());
		if (this.produit.getIdProduit() != 0) {
			return "produit";
		} else
			return "produit";
	}

	public String ajouterProduit() {

		this.produit = produitService.addProduitStock(this.produit, this.categorie);

		if (this.produit.getIdProduit() != 0) {
			// recup nouvelle liste
			this.listeProduit = produitService.getAllProduit();

			// mettre a jour la liste dans la sesion
			maSession.setAttribute("produitList", this.listeProduit);

			return "gestionStock";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit non Ajoutée !"));
			return "ajoutProduit";
		}

	}

	public String supprimeProduit() {

		int p = produitService.deleteProduitStock(this.produit.getIdProduit());

		if (p != 0) {
			// recup nouvelle liste
			this.listeProduit = produitService.getAllProduit();
			// mettre a jour la liste dans la sesion
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitList", listeProduit);
			return "gestionStock";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produit non Supprimé !"));
			return "supprimeProduit";
		}

	}

	// transformer une image uploadfile en byte array
	public void upload(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();

		// recup contenu de l'image en byte array (pixels)
		byte[] contents = uploadedFile.getContents();
		// stock dans l'atttribut photo de la catégorie
		produit.setPhoto(contents);
		// tranformer byte array en string (format base64)
		this.image = "data:image/png;base64," + Base64.encodeBase64String(contents);

	}

	public void getAllProduit() {

		List<Produit> listOut = produitService.getAllProduit();
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

	// public void createPDF() {
	// Document document = new Document();
	// try {
	// PdfWriter.getInstance(document, new FileOutputStream(chemin));
	// document.open();
	//
	// document.add(new Paragraph("PDF recaptitulatif"));
	// document.add(new Paragraph("\n"));
	//
	// } catch (DocumentException de) {
	// de.printStackTrace();
	// } catch (IOException ioe) {
	// ioe.printStackTrace();
	// }
	//
	// document.close();
	//
	// }
	//
	// // Classe permettant de dessiner un tableau.
	//
	// public PdfPTable produitTableau() {
	// List<Produit> listOut = produitService.getAllProduit();
	//
	// // On créer un objet table dans lequel on intialise sa taille.
	// PdfPTable table = new PdfPTable(3);
	//
	// // On créer l'objet cellule.
	// PdfPCell cell;
	//
	// cell = new PdfPCell(new Phrase("Information Liste Produit"));
	// cell.setColspan(3);
	// table.addCell(cell);
	//
	// // contenu du tableau.
	// for (Produit e : listOut) {
	// // ajout colonne produit
	// cell = new PdfPCell(new Phrase("Produit " + e.getIdProduit()));
	// cell.setRowspan(2);
	// table.addCell(cell);
	// // ajout colonne specifique au produit
	// table.addCell("Designation " + e.getDesignation());
	// table.addCell("Description " + e.getDescription());
	// table.addCell("Prix " + e.getPrix());
	// table.addCell("Quantité " + e.getQuantite());
	// }
	//
	// return table;
	// }

	// public void sendEmail() {
	//
	// // adresse de l'expediteur
	// final String username = "anais.guelennoc@gmail.com";
	// // mdp de la boite mail
	// final String password = "Brpg6SxZF";
	//
	// Properties props = new Properties();
	// props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.starttls.enable", "true");
	// props.put("mail.smtp.host", "smtp.gmail.com");
	// props.put("mail.smtp.port", "587");
	//
	// // identification des user et password dans la session
	// Session session = Session.getInstance(props, new
	// javax.mail.Authenticator() {
	// protected PasswordAuthentication getPasswordAuthentication() {
	// return new PasswordAuthentication(username, password);
	// }
	// });
	//
	// try {
	// List<Produit> listOut = produitService.getAllProduit();
	// Message message = new MimeMessage(session);
	// message.setFrom(new InternetAddress("from-email@gmail.com"));
	// // adresse reception
	// message.setRecipients(Message.RecipientType.TO,
	// InternetAddress.parse("guelennoc.anais@gmail.com"));
	// // titre du mail
	// message.setSubject("Liste des Produits");
	// // contenu du mail
	// message.setText("liste des Produits : ");
	//
	// // envoie du mail
	// Transport.send(message);
	//
	// System.out.println("Done");
	//
	// } catch (MessagingException e) {
	// throw new RuntimeException(e);
	// }
	// }

	public String seConnecter() {

		// recup liste produit
		this.getAllProduit();

		// ajouter les listes deans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitList", listeProduit);

		return "produit.xhtml";

	}
public String seConnectergoogleMap(){
		
		return "googleMaps2.xhtml";
	}
	public String quiSommesNous(){
		
		return "quiSommesNous.xhtml";
	}

}

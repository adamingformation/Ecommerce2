package fr.adaming.managedBean;

import java.io.Serializable;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.Panier;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;

@ManagedBean(name = "cliMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	@ManagedProperty(value = "#{clService}")
	private IClientService clientService;
	@ManagedProperty(value = "#{comService}")
	private ICommandeService commandeService;

	private Client client;
	private Commande commande;
	private List<Commande> listeCommandes;
	private Panier panier;

	// constructeur par defaut
	public ClientManagedBean() {
		this.client = new Client();
	}

	// Getters et Setters

	public Client getClient() {
		return client;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public List<Commande> getListeCommandes() {
		return listeCommandes;
	}

	public void setListeCommandes(List<Commande> listeCommandes) {
		this.listeCommandes = listeCommandes;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	// Les Méthodes
	public String ajouterClient() {
		this.client = clientService.addClient(this.client);
		// Passer le client dans la session
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("client", this.client);

		// recup de la commande avant son enregistrement
		this.commande = commandeService.getCommandeByIdClNULL(this.client.getIdClient());

		// associe le client a la commande
		this.commande.setClient(this.client);
		this.commande = commandeService.updateCommande(this.commande);

		if (this.client.getIdClient() != 0) {
			return "accueil";
		} else {
			return "loginClient";
		}
	}

	public String supprimerClient() {
		clientService.deleteClient(this.client.getIdClient());
		if (this.client.getIdClient() == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Votre compte n'existe plus"));
			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("la suppression du compte a échoué"));
			return "supprimerClient";
		}

	}

	public String modifierClient() {
		Client clOut = clientService.updateClient(this.client);
		if (clOut != null) {
			return "accueil";
		} else {
			return "modifierClient";
		}

	}

	public String rechercherClient() {
		Client clOut = clientService.getClientByNomEmail(this.client.getNomClient(), this.client.getEmail());

		if (clOut != null) {
			this.client = clOut;

			// Passer le client dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("client", this.client);

			// Récupérer la commande effectuée par le client avant son
			// enregistremnt
			this.commande = commandeService.getCommandeByIdClNULL(this.client.getIdClient());

			// associer le client a la commande
			this.commande.setClient(this.client);
			this.commande = commandeService.updateCommande(this.commande);

			return "accueilClient";
		} else {
			return "loginClient";
		}

	}

	public void deconnexionClient() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

	}

}

package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Produit;

public interface IProduitDao {

	public Produit addProduitStock(Produit p);

	public List<Produit> getAllProduit();

	public Produit getProduitById(long id);

	public int deleteProduitStock(long id);

	public Produit updateProduit(Produit p);

	public List<Produit> getAllPorduitByCategorie(int id_c);

}

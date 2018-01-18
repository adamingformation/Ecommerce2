package fr.adaming.service;

import java.util.List;



import fr.adaming.model.Categorie;


public interface ICategorieService {

public List<Categorie> getAllCategorie();
	
	public Categorie getCategorieById(long id);
	
	public Categorie addCategorie(Categorie C);
	
	public long deleteCategorie(long id);

	public Categorie updateCategorie (Categorie C);
}

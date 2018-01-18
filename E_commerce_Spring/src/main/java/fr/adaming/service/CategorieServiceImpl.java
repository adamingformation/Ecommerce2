package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Categorie;

@Service("catService")
@Transactional
public class CategorieServiceImpl implements ICategorieService{

	
	@Autowired
	private ICategorieDao categorieDao;
	
	
	public void setCategorieDao(ICategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	@Override
	public List<Categorie> getAllCategorie() {
		
		return categorieDao.getAllCategorie();
	}

	@Override
	public Categorie getCategorieById(long id) {
		
		return categorieDao.getCategorieById(id);
	}

	@Override
	public Categorie addCategorie(Categorie C) {
	
				return categorieDao.addCategorie(C);
	}

	@Override
	public long deleteCategorie(long id) {
		
		return categorieDao.deleteCategorie(id);
	}

	@Override
	public Categorie updateCategorie(Categorie C) {
		
		return categorieDao.updateCategorie(C);
	}

}

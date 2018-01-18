package fr.adaming.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdminDao;
import fr.adaming.model.Admin;

@Service("aService")
@Transactional //pour rendre les methodes de la classe transactionnelles
public class AdminServiceImpl implements IAdminServive {

	//pour injecter un Dao
	@Autowired
	private IAdminDao adminDao;
	
	
	//getters et setters pour l'injection de dependance
	public IAdminDao getAdminDao() {
		return adminDao;
	}



	public void setAdminDao(IAdminDao adminDao) {
		this.adminDao = adminDao;
	}



	@Override
	public Admin isExist(Admin a) throws Exception {
		
		return adminDao.isExist(a);
	}

}

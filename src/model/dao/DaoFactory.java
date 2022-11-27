package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		
		return new SellerDaoJDBC(); //macete para esconder a implementa��o. Ao instanciar a classe SellerDao chama-se esse m�todo, e a implementa��o fica oculta.
	}
}
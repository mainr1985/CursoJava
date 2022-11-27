package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		
		return new SellerDaoJDBC(); //macete para esconder a implementação. Ao instanciar a classe SellerDao chama-se esse método, e a implementação fica oculta.
	}
}
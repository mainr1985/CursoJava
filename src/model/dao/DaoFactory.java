package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		
		return new SellerDaoJDBC(DB.getConection()); //macete para esconder a implementação. Ao instanciar a classe SellerDao chama-se esse método, e a implementação fica oculta.
	}
}
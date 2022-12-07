package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		//Seller seller = sellerDao.findById(1);	
		Department dep = new Department(2,null);
		//List<Seller> list = sellerDao.findByDepartment(dep);
		List<Seller> list = sellerDao.findAll();
		for(Seller obj:list) { //forEach
			System.out.println(obj);	
		}			
		
		Seller newSeller = new Seller(null,"Greg","greg@gmail.com",new Date(),4000.0,dep);
		sellerDao.insert(newSeller);
		System.out.println("Inserted. New id = " + newSeller.getId());
	}
}
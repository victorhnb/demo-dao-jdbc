package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		
	SellerDao sellerDao = DaoFactory.createSellerDao();
	
	System.out.println("\n=== TEST 1: seller findById =====\n");
	
	Seller s = sellerDao.findById(3);
	
	System.out.println(s);
	
	System.out.println("\n=== TEST 2: seller findByDepartment =====\n");
	
	Department dp = new Department(2, null);
	
	List<Seller> list = sellerDao.findByDepartment(dp);
	for (Seller obj : list) {
		System.out.println(obj);
	}

	
System.out.println("\n=== TEST 3: seller findByAll =====\n");
	
	
	 list = sellerDao.findAll();
	for (Seller obj : list) {
		System.out.println(obj);
	}

	
	
	
	}

}

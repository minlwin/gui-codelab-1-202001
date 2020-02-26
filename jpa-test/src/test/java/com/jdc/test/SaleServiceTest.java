package com.jdc.test;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import com.jdc.jpa.entity.Category;
import com.jdc.jpa.entity.Customer;
import com.jdc.jpa.entity.Product;
import com.jdc.jpa.entity.Sale;
import com.jdc.jpa.entity.SaleDetails;
import com.jdc.jpa.service.BaseService;

public class SaleServiceTest extends AbstractTest<Sale, Serializable>{

	public SaleServiceTest() {
		super(Sale.class);

		EntityManager em = EMF.createEntityManager();

		BaseService<Category, Integer> catService = new BaseService<>(em, Category.class);
		Category c = new Category();
		c.setName("Chinese");
		catService.create(c);
		
		BaseService<Product, Integer> proService = new BaseService<>(em, Product.class);
		p = new Product();
		p.setCategory(c);
		p.setName("Fride Noodle");
		p.setSize("Large");
		p.setPrice(5000);
		proService.create(p);
		
		em.close();
	}
	
	private static Product p;

	@Test
	@Override
	void createTest() {

		Sale s = new Sale();
		s.setDeliveryFees(200);
		s.setSaleDate(LocalDate.now());
		s.setSaleTime(LocalTime.now());
		
		Customer cust = new Customer();
		cust.setName("Aung Aung");
		s.setCustomer(cust);
		
		SaleDetails sd = new SaleDetails();
		sd.setProduct(p);
		sd.setPrice(p.getPrice());
		sd.setQuantity(2);
		
		s.addDetaills(sd);
		
		service.create(s);
	}

	@Override
	void findByIdTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void updateTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void findAllTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void deleteTest() {
		// TODO Auto-generated method stub
		
	}

}

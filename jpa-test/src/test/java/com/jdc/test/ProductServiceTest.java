package com.jdc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.jdc.jpa.entity.Category;
import com.jdc.jpa.entity.Product;
import com.jdc.jpa.service.BaseService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest extends AbstractTest<Product, Integer>{

	public ProductServiceTest() {
		
		super(Product.class);
		
		EntityManager em = EMF.createEntityManager();

		BaseService<Category, Integer> catService = new BaseService<>(em, Category.class);
		c = new Category();
		c.setName("Chinese");
		catService.create(c);
		
		em.close();
	}
	
	private final Category  c;

	@Test
	@Order(1)
	@Override
	void createTest() {

		Product p = new Product();
		p.setCategory(c);
		p.setName("Fride Noodle");
		p.setSize("Large");
		p.setPrice(5000);
		
		service.create(p);
		
		assertEquals(1, p.getId());
	}

	@Test
	@Order(2)
	@Override
	void findByIdTest() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(3)
	@Override
	void updateTest() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(4)
	@Override
	void findAllTest() {
		// TODO Auto-generated method stub
		
	}

	@Test
	@Order(5)
	@Override
	void deleteTest() {
		// TODO Auto-generated method stub
		
	}

}

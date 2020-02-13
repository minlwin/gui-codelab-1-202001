package com.jdc.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.jdc.jpa.entity.Category;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryServiceTest extends AbstractTest<Category, Integer> {
	
	public CategoryServiceTest() {
		super(Category.class);
	}

	@Test
	@Order(1)
	@Override
	void createTest() {
		Category c = new Category();
		c.setName("Chinese");
		
		service.create(c);
		
		assertEquals(1, c.getId());
	}
	
	@Test
	@Order(2)
	@Override
	void findByIdTest() {
		Category c = service.findById(1);
		assertNotNull(c);
		assertEquals("Chinese", c.getName());
		
		Category c2 = service.findById(2);
		assertNull(c2);
	}
	
	@Test
	@Order(3)
	@Override
	void updateTest() {
		Category c = service.findById(1);
		c.setName("Curry");
		
		service.update(c);
		
		Category c2 = service.findById(1);
		assertEquals("Curry", c2.getName());
	}
	
	@Test
	@Order(4)
	@Override
	void findAllTest() {
		
		List<Category> list = service.findAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@Order(5)
	@Override
	void deleteTest() {
		service.deleteById(1);
		
		assertNull(service.findById(1));
	}

}

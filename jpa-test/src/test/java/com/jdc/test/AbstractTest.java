package com.jdc.test;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.jdc.jpa.service.BaseService;

public abstract class AbstractTest<E, ID extends Serializable> {

	static EntityManagerFactory EMF;
	EntityManager em;
	protected BaseService<E, ID> service;
	Class<E> type;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		EMF = Persistence.createEntityManagerFactory("jpa-test");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		EMF.close();
	}

	public AbstractTest(Class<E> type) {
		super();
		this.type = type;
	}

	@BeforeEach
	void setUp() throws Exception {
		em = EMF.createEntityManager();
		service = new BaseService<E, ID>(em, type);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}
	
	abstract void createTest();
	
	abstract void findByIdTest();
	
	abstract void updateTest();
	
	abstract void findAllTest();
	
	abstract void deleteTest();

}
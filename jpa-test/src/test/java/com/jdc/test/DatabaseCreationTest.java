package com.jdc.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DatabaseCreationTest {
	
	static EntityManagerFactory EMF;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		EMF = Persistence.createEntityManagerFactory("jpa-test");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		EMF.close();
	}

	@Test
	void test() {

	}

}

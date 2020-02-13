package com.jdc.jpa.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class BaseService<E, ID extends Serializable> {
	
	protected EntityManager em;
	
	private Class<E> entityType;
	
	public BaseService(EntityManager em, Class<E> entityType) {
		super();
		this.em = em;
		this.entityType = entityType;
	}
	
	public void create(E entity) {
		
		em.getTransaction().begin();
		
		em.persist(entity);
		
		em.getTransaction().commit();
	}

	public void update(E entity) {
		em.getTransaction().begin();
		
		em.merge(entity);
		
		em.getTransaction().commit();
	}

	public  E findById(ID id) {
		return em.find(entityType, id);
	}

	public List<E> findAll() {
		TypedQuery<E> query = em.createNamedQuery(String.format("%s.%s", entityType.getSimpleName(), "findAll"), entityType);
		return query.getResultList();
	}
	
	public void deleteById(ID id) {
		em.getTransaction().begin();
		Query query = em.createNamedQuery(String.format("%s.%s", entityType.getSimpleName(), "deleteById"));
		query.setParameter("id", id);
		query.executeUpdate();
		em.getTransaction().commit();
	}
}

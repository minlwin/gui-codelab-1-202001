package com.jdc.students.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class StudentService {

	private EntityManager em;

	public StudentService(EntityManager em) {
		super();
		this.em = em;
	}

	public List<Student> search(String name, String phone) {

		StringBuilder sb = new StringBuilder("select s from Student s where 1 = 1");
		Map<String, Object> params = new HashMap<>();
		
		if (!isEmpty(name)) {
			sb.append(" and LOWER(s.name) like LOWER(:name)");
			params.put("name", name.concat("%"));
		}

		if (!isEmpty(phone)) {
			sb.append(" and s.phone like :phone");
			params.put("phone", phone.concat("%"));
		}
		
		TypedQuery<Student> q = em.createQuery(sb.toString(), Student.class);
		
		for(String key : params.keySet()) {
			q.setParameter(key, params.get(key));
		}

		return q.getResultList();
	}

	public void save(Student student) {

		validate(student);

		em.getTransaction().begin();

		if (student.getId() == 0) {
			em.persist(student);
		} else {
			em.merge(student);
		}

		em.getTransaction().commit();
	}

	private void validate(Student student) {

		if (null == student) {
			throw new RuntimeException("Student must not be null!");
		}

		if (isEmpty(student.getName())) {
			throw new RuntimeException("Please enter Student Name!");
		}

		if (isEmpty(student.getPhone())) {
			throw new RuntimeException("Please enter Student's Phone!");
		}

		if (isEmpty(student.getEmail())) {
			throw new RuntimeException("Please enter Student's Email!");
		}
	}

	private boolean isEmpty(String str) {
		return null == str || str.isEmpty();
	}

}

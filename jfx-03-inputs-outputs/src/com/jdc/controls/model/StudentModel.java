package com.jdc.controls.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {
	
	private static final String FILE = "student.obj";
	private List<Student> list;
	
	@SuppressWarnings("unchecked")
	private StudentModel() {
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
			Object obj = in.readObject();
			list = (List<Student>) obj;
		} catch (Exception e) {
			list = new ArrayList<>();
		}
	}
	
	private static StudentModel model;

	public static StudentModel getInstance() {
		
		if(null == model) {
			model = new StudentModel();
		}
		
		return model;
	}

	public List<Student> getAllStudents() {
		return new ArrayList<>(list);
	}

	public void save(Student student) {

		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
			list.add(student);
			out.writeObject(list);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

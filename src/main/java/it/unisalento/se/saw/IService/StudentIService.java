package it.unisalento.se.saw.IService;

import java.util.List;


import it.unisalento.se.saw.domain.Student;

public interface StudentIService {

	public Student findById(Integer id);
	public void saveStudent(Student student);
	public void updateStudent(Student student);
	public void deleteStudentById(Integer id);
	public List<Student> findAllStudents();
	public boolean isStudentExist(Student student);
}

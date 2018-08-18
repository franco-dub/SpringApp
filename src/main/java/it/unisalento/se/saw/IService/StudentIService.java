package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.StudentDto;

public interface StudentIService {

	public StudentDto findById(Integer id);
	public void saveStudent(StudentDto studentDto);
	public void updateStudent(StudentDto studentDto);
	public void deleteStudentById(Integer id);
	public List<StudentDto> findAllStudents();
	public StudentDto findByPerson(PersonDto personDto);
}

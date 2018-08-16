package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.repo.StudentRepository;

@Service
public class StudentService implements StudentIService{
	StudentRepository studentRepository;
	PersonIService personService;
	CourseIService courseService;
	@Autowired
	public StudentService(StudentRepository studentRepository, PersonIService personService,
			CourseIService courseService) {
		super();
		this.studentRepository = studentRepository;
		this.personService = personService;
		this.courseService = courseService;
	}
	@Override
	@Transactional
	public Student findById(Integer id) {
		return studentRepository.findById(id).get();
	}
	@Override
	@Transactional
	public void saveStudent(Student student) {
		personService.savePerson(student.getPerson()); //NON DEVE SALVARE IL CORSO MA LO DEVE CERCARE
		courseService.saveCourse(student.getCourse());
		studentRepository.save(student);
	}
	@Override
	@Transactional
	public void updateStudent(Student student) {
		Integer idPerson = findById(student.getStudentId()).getPerson().getPersonId();
		student.getPerson().setPersonId(idPerson);
		saveStudent(student);
	}
	@Override
	@Transactional
	public void deleteStudentById(Integer id){
		Integer idPerson = findById(id).getPerson().getPersonId();
		studentRepository.deleteById(id);
		personService.deletePersonById(idPerson);
		
	}
	@Override
	@Transactional
	public List<Student> findAllStudents(){
		return studentRepository.findAll();
	}
	@Override
	@Transactional
	public boolean isStudentExist(Student student) {
		return findById(student.getStudentId()) != null;
	}

	
}

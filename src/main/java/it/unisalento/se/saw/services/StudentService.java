package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.repo.StudentRepository;

@Service
public class StudentService implements StudentIService{
	private static final ModelMapper modelMapper = new ModelMapper();
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
	public StudentDto findById(Integer id) {
		Student student = studentRepository.findById(id).get();
		StudentDto studentDto = modelMapper.map(student, StudentDto.class);
		return studentDto;
	}
	@Override
	@Transactional
	public void saveStudent(StudentDto studentDto) {
		Student student = modelMapper.map(studentDto, Student.class);
		studentRepository.save(student);
	}
	@Override
	@Transactional
	public void updateStudent(StudentDto studentDto) {
		Integer idPerson = findById(studentDto.getStudentId()).getPerson().getPersonId();
		studentDto.getPerson().setPersonId(idPerson);
		saveStudent(studentDto);
	}
	@Override
	@Transactional
	public void deleteStudentById(Integer id){
		studentRepository.deleteById(id);
		
	}
	@Override
	@Transactional
	public List<StudentDto> findAllStudents(){
		List<Student> students = studentRepository.findAll();
		Type targetListType = new TypeToken<List<StudentDto>>() {}.getType();
		List<StudentDto> studentDtos = modelMapper.map(students, targetListType);
		return studentDtos;
	}

	@Transactional
	public StudentDto findByPerson(PersonDto personDto) {
		try {
			Person person = modelMapper.map(personDto, Person.class);
			Student student = studentRepository.findByPerson(person);
			StudentDto studentDto = modelMapper.map(student, StudentDto.class);
			return studentDto;
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	@Override
	@Transactional
	public List<StudentDto> findAllCourseSStudent(Integer courseId) {
		List<Student> students = studentRepository.findAllByCourseCourseId(courseId);
		Type targetListType = new TypeToken<List<StudentDto>>() {}.getType();
		List<StudentDto> studentDtos = modelMapper.map(students, targetListType);
		return studentDtos;
	}
}

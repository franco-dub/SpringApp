package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.domain.Student;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@RequestMapping(path = "/student")
public class StudentController {
	
	StudentIService studentService;

	@Autowired
	public StudentController(StudentIService studentService) {
		super();
		this.studentService = studentService;
	}
	
	// -------------------Create a Student-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDto studentDto) {
    	try {
    		studentService.saveStudent(studentDto);
            return new ResponseEntity<StudentDto>(studentDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
			System.out.println(e.toString());
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Student. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
    //-------------------Retrieve All Student--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllStudents() {
    	List<StudentDto> students = studentService.findAllStudents();
    	if (students.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<StudentDto>>(students, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single Student------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
    	try {
    		StudentDto studentDto = studentService.findById(id);
    		return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Student with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Update a Student ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStudent(@PathVariable("id") int id, 
    		@Valid @RequestBody StudentDto studentDto) {
    	try {
    		studentService.findById(id);
    		try {
    			studentDto.setStudentId(id);
    			studentService.updateStudent(studentDto);
                return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Student. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Student with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    //------------------- Delete a Student --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Student with id " + id);
        try {
        	studentService.findById(id);
        	studentService.deleteStudentById(id);
            return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Student with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
    
//-------------------Retrieve All Course's Students--------------------------------------------------------
    
    @RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCourseSStudents(@PathVariable("id") Integer id) {
    	List<StudentDto> students = studentService.findAllCourseSStudent(id);
    	if (students.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<StudentDto>>(students, HttpStatus.OK);
    }
}

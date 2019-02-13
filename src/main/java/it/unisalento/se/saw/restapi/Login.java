package it.unisalento.se.saw.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.dto.LoginDto;
import it.unisalento.se.saw.dto.LoginDto.Type;
import it.unisalento.se.saw.dto.PersonDto;

@RestController
@CrossOrigin
@RequestMapping(path = "/login")
public class Login {
	
	PersonIService personService;
	ProfessorIService professorService;
	StudentIService studentService;
	SecretaryIService secretaryService;
	
	@Autowired
	public Login(PersonIService personService, ProfessorIService professorService, StudentIService studentService,
			SecretaryIService secretaryService) {
		super();
		this.personService = personService;
		this.professorService = professorService;
		this.studentService = studentService;
		this.secretaryService = secretaryService;
	}

	@RequestMapping(value = "/{email}/{password}", method = RequestMethod.GET)
    public ResponseEntity<?> log(@PathVariable("email") String email,@PathVariable("password") String password) {
    		PersonDto personDto = personService.findByEmailAndPassword(email, password);
    		LoginDto login = new LoginDto();
    		if(professorService.findByPerson(personDto)!=null) {
    			login.setType(Type.PROFESSOR);
    			login.setProfessor(professorService.findByPerson(personDto));
    		} else if(studentService.findByPerson(personDto)!=null){
    			login.setType(Type.STUDENT);
    			login.setStudent(studentService.findByPerson(personDto));
    		} else if(secretaryService.findByPerson(personDto)!=null){
    			login.setType(Type.SECRETARY);
    			login.setSecretary(secretaryService.findByPerson(personDto));
    		} else
    			login.setType(Type.NOT_FOUND);
    		
    		return new ResponseEntity<>(login, HttpStatus.OK);
    }
}

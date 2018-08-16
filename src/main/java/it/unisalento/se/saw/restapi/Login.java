package it.unisalento.se.saw.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import it.unisalento.se.saw.repo.ProfessorRepository;

@RestController
@RequestMapping(path = "/login")
public class Login {
	
	PersonIService personService;
	ProfessorRepository professorRepository;
	
	@Autowired
	public Login(PersonIService personService,ProfessorRepository professorRepository) {
		super();
		this.personService = personService;
		this.professorRepository=professorRepository;
	}

	@RequestMapping(value = "/{email}/{password}", method = RequestMethod.GET)
    public ResponseEntity<?> log(@PathVariable("email") String email,@PathVariable("password") String password) {
    		Person person = personService.findByMail(email);
    		Professor prof=professorRepository.findByPerson(person);
    		if(prof!=null)
    		return new ResponseEntity<String>("professor", HttpStatus.OK);
    		else
    			return new ResponseEntity<String>("cazzi", HttpStatus.OK);
    }
}

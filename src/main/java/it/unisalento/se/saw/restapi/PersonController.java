package it.unisalento.se.saw.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.services.PersonService;

@RestController
@RequestMapping(path = "/person")
public class PersonController {
	
	PersonService personService;
	
	@Autowired
	public PersonController(PersonService personService) {
		super();
		this.personService = personService;
	}

	//-------------------Retrieve All Persons--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> persons = personService.findAllPersons();
        if(persons.isEmpty()){
            return new ResponseEntity<List<Person>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }
	

}

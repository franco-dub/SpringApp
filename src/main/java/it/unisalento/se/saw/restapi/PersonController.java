package it.unisalento.se.saw.restapi;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import it.unisalento.se.saw.util.Dto;

@RestController
@RequestMapping(path = "/person")
public class PersonController {

	PersonIService personService;
	
	@Autowired
    public PersonController(PersonIService personService) {
    	super();
		this.personService = personService;
	}

	//-------------------Retrieve All Persons--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> persons = personService.findAllPersons();
        if (persons.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
    }
    
    
 // -------------------Create a Person-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@RequestBody @Validated @Dto(PersonDto.class)Person person, 
    		UriComponentsBuilder ucBuilder) {
      /*  logger.info("Creating User : {}", user);
    	
    	if( !personService.findByLastName(personDto.getLastName()).isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to create. A Person with last name " + 
    	            personDto.getLastName() + " already exist."),HttpStatus.CONFLICT);
    	}
    	Person person = new Person(personDto.getFirstName(), personDto.getLastName(), 
    			personDto.getEmail(), personDto.getPhone(), personDto.getDateOfBirth(),
    			personDto.getGender(), personDto.getPassword());
    	personService.savePerson(person);*/
    	//ModelMapper modelMapper = new ModelMapper();
    	//Person person = modelMapper.map(personDto, Person.class);
    	personService.savePerson(person);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/{id}").buildAndExpand(person.getPersonId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
// -------------------Retrieve Single Person------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") int id) {
       // logger.info("Fetching User with id {}", id);
        Person person = personService.findById(id);
        if (person == null) {
           // logger.error("User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Person with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Person>(person, HttpStatus.OK);
    }
    
// ------------------- Update a User ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@PathVariable("id") int id, @RequestBody PersonDto personDto) {
        //logger.info("Updating User with id {}", id);
 
        Person currentPerson = personService.findById(id);
 
        if (currentPerson == null) {
            //logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<>(new CustomErrorType("Unable to upate. Person with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
        }
        
        currentPerson.setFirstName(personDto.getFirstName());
        currentPerson.setLastName(personDto.getLastName());
        currentPerson.setEmail(personDto.getEmail());
        currentPerson.setPhone(personDto.getPhone());
        currentPerson.setDateOfBirth(personDto.getDateOfBirth());
        currentPerson.setGender(personDto.getGender());
        currentPerson.setPassword(personDto.getPassword());

        personService.updatePerson(currentPerson);
        return new ResponseEntity<Person>(currentPerson, HttpStatus.OK);
    }
	

}

package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/person")
public class PersonController {

	PersonIService personService;
	
	@Autowired
    public PersonController(PersonIService personService) {
    	super();
		this.personService = personService;
	}

	//-------------------Retrieve All "Persons"--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllPersons() {
    	List<PersonDto> persons = personService.findAllPersons();
    	if (persons.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<PersonDto>>(persons, HttpStatus.OK);
    }
    
    
 // -------------------Create a Person-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonDto personDto) {
    	try {

            return new ResponseEntity<PersonDto>(personService.savePerson(personDto), HttpStatus.CREATED);
    	} catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Person. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
// -------------------Retrieve Single Person------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") int id) {
    	try {
    		PersonDto personDto = personService.findById(id);
    		return new ResponseEntity<PersonDto>(personDto, HttpStatus.OK);
    	} catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Person with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Person ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@PathVariable("id") int id, 
    		@Valid @RequestBody PersonDto personDto) {
    	try {
    		personService.findById(id);
    		try {
    			personService.updatePerson(personDto, id);
                return new ResponseEntity<PersonDto>(personDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Person. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Person with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
	
  //------------------- Delete a Person --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable("id") Integer id) {
        System.out.println("Fetching & Deleting Person with id " + id);
        try {
        	personService.findById(id);
        	personService.deletePersonById(id);
            return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Person with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
 
     
    //------------------- Delete All Person --------------------------------------------------------
     
    @RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
    public ResponseEntity<Person> deleteAllPersons() {
        System.out.println("Deleting All Persons");
 
        personService.deleteAllPersons();
        return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
    }

}

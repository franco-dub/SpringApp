package it.unisalento.se.saw.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.exceptions.ResourceNotFoundException;
import it.unisalento.se.saw.repo.PersonRepository;

@Service
public class PersonService {
	
	PersonRepository personRepository;
	@Autowired
	public PersonService(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	//private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	
	@Transactional
    public Person findById(Integer id) throws ResourceNotFoundException {
    	try {
    		return personRepository.getOne(id);
    	} catch (Exception e) {
    		throw new ResourceNotFoundException("Person", "id", id);
    	}
        
    }
	
	@Transactional
    public List<Person> findByLastName(String lastName) throws ResourceNotFoundException {
    	try {
    		return personRepository.findByLastName(lastName);
    	} catch (Exception e) {
    		throw new ResourceNotFoundException("Person-list", "lastName" , lastName);
    	}
        
    }
    
    @Transactional
    public void savePerson(Person person) {
        personRepository.save(person);
    }
 
    @Transactional
    public void updatePerson(Person personDetails){
    	Integer id = personDetails.getPersonId();
    	Person person = personRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
        
    	person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setEmail(personDetails.getEmail());
        person.setPhone(personDetails.getPhone());
        person.setDateOfBirth(personDetails.getDateOfBirth());
        person.setGender(personDetails.getGender());
        person.setPassword(personDetails.getPassword());
        
    	savePerson(person);
    }
 
    @Transactional
    public void deletePersonById(Integer id){
    	personRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
    	personRepository.deleteById(id);
    }
 
    @Transactional
    public void deleteAllPersons(){
        personRepository.deleteAll();
    }
    
    //@Transactional(readOnly=true)
    public List<Person> findAllPersons(){
        return personRepository.findAll();
    }
    
    
    public boolean isPersonExist(Person person) {
        return findById(person.getPersonId()) != null;
    }
    
}

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
public class PersonService implements PersonIService {
	
	PersonRepository personRepository;
	

	//private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public PersonService(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	@Override
	@Transactional
    public Person findById(Integer id) throws ResourceNotFoundException {
    	try {
    		return personRepository.getOne(id);
    	} catch (Exception e) {
    		throw new ResourceNotFoundException("Person", "id", id);
    	}
        
    }
	
	@Override
	@Transactional
    public List<Person> findByLastName(String lastName) throws ResourceNotFoundException {
    	try {
    		return personRepository.findByLastName(lastName);
    	} catch (Exception e) {
    		throw new ResourceNotFoundException("Person-list", "lastName" , lastName);
    	}
        
    }
    
	@Override
    @Transactional
    public void savePerson(Person person) {
        personRepository.save(person);
    }
 
	@Override
    @Transactional
    public void updatePerson(Person person){
    	savePerson(person);
    }
 
	@Override
    @Transactional
    public void deletePersonById(Integer id){
    	personRepository.findById(id)
    			.orElseThrow(() -> new ResourceNotFoundException("Person", "id", id));
    	personRepository.deleteById(id);
    }
 
	@Override
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

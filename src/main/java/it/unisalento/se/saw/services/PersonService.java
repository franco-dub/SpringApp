package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.repo.PersonRepository;

@Service
public class PersonService implements PersonIService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	PersonRepository personRepository;
	//private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	public PersonService(PersonRepository personRepository) {
		super();
		this.personRepository = personRepository;
	}

	@Override
	@Transactional
    public PersonDto findById(Integer id) {
		Person person = personRepository.findById(id).get();
		PersonDto personDto = modelMapper.map(person, PersonDto.class);
		return personDto;
    }
    
	@Override
    @Transactional
    public void savePerson(PersonDto personDto) {
		Person person = modelMapper.map(personDto, Person.class);
        personRepository.save(person);
    }
 
	@Override
    @Transactional
    public void updatePerson(PersonDto personDto, Integer id){
		Person person = modelMapper.map(personDto, Person.class);
		person.setPersonId(id);
		personRepository.save(person);
    }
 
	@Override
    @Transactional
    public void deletePersonById(Integer id){
    	personRepository.deleteById(id);
    }
 
	@Override
    @Transactional
    public void deleteAllPersons(){
        personRepository.deleteAll();
    }
    @Override
    @Transactional(readOnly=true)
    public List<PersonDto> findAllPersons(){
    	List<Person> persons = personRepository.findAll();
    	Type targetListType = new TypeToken<List<PersonDto>>() {}.getType();
    	    List<PersonDto> personDtos = modelMapper.map(persons, targetListType);
        return personDtos;
    }    
}

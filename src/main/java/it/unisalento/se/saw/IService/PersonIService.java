package it.unisalento.se.saw.IService;

import java.util.List;
import java.util.Optional;

import it.unisalento.se.saw.domain.Person;

public interface PersonIService {
	public Person findById(Integer id);
	public void savePerson(Person person);
	public void updatePerson(Person person);
	public void deletePersonById(Integer id);
	public void deleteAllPersons();
	public List<Person> findAllPersons();
	public boolean isPersonExist(Person person);
}
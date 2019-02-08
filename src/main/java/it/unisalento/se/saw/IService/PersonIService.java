package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.PersonDto;

public interface PersonIService {
	public PersonDto findById(Integer id);
	public PersonDto savePerson(PersonDto personDto);
	public void updatePerson(PersonDto personDto, Integer id);
	public void deletePersonById(Integer id);
	public void deleteAllPersons();
	public List<PersonDto> findAllPersons();
	public PersonDto findByMail(String email);
}
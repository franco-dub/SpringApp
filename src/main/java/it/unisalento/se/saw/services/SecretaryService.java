package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.domain.Secretary;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.dto.SecretaryDto;
import it.unisalento.se.saw.repo.SecretaryRepository;
import it.unisalento.se.saw.util.Dto;

@Service
public class SecretaryService implements SecretaryIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	PersonIService personService;
	SecretaryRepository secretaryRepository;
	@Autowired
	public SecretaryService(PersonIService personService, SecretaryRepository secretaryRepository) {
		super();
		this.personService = personService;
		this.secretaryRepository = secretaryRepository;
	}

	@Override
	@Transactional
	public SecretaryDto findById(Integer id) {
		Secretary secretary = secretaryRepository.findById(id).get();
		SecretaryDto secretaryDto = modelMapper.map(secretary, SecretaryDto.class);
		return secretaryDto;
	}

	@Override
	@Transactional
	public void saveSecretary(SecretaryDto secretaryDto) {
		Secretary secretary = modelMapper.map(secretaryDto, Secretary.class);
		personService.savePerson(secretaryDto.getPerson());
		secretaryRepository.save(secretary);
	}

	@Override
	@Transactional
	public void updateSecretary(SecretaryDto secretaryDto) {
		Secretary secretary = modelMapper.map(secretaryDto, Secretary.class);
		Integer idPerson = findById(secretary.getSecretaryId()).getPerson().getPersonId();
		secretary.getPerson().setPersonId(idPerson);
		saveSecretary(secretaryDto);
	}

	@Override
	@Transactional
	public void deleteSecretaryById(Integer id) {
		Integer idPerson = findById(id).getPerson().getPersonId();
		secretaryRepository.deleteById(id);
		personService.deletePersonById(idPerson);
	}

	@Override
	@Transactional
	public List<SecretaryDto> findAllSecretaries() {
		List<Secretary> secretaries = secretaryRepository.findAll();
		Type targetListType = new TypeToken<List<SecretaryDto>>() {}.getType();
    	List<SecretaryDto> secretaryDtos = modelMapper.map(secretaries, targetListType);
        return secretaryDtos;
	}
}

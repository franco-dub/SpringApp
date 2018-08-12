package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.domain.Secretary;
import it.unisalento.se.saw.repo.SecretaryRepository;

@Service
public class SecretaryService implements SecretaryIService {

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
	public Secretary findById(Integer id) {
		return secretaryRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void saveSecretary(Secretary secretary) {
		personService.savePerson(secretary.getPerson());
		secretaryRepository.save(secretary);
	}

	@Override
	@Transactional
	public void updateSecretary(Secretary secretary) {
		Integer idPerson = findById(secretary.getSecretaryId()).getPerson().getPersonId();
		secretary.getPerson().setPersonId(idPerson);
		saveSecretary(secretary);
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
	public List<Secretary> findAllSecretaries() {
		return secretaryRepository.findAll();
	}

	@Override
	@Transactional
	public boolean isSecretaryExist(Secretary secretary) {
		return findById(secretary.getSecretaryId()) != null;
	}

}

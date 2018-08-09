package it.unisalento.se.saw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.repo.ProfessorRepository;

@Service
public class ProfessorService implements ProfessorIService{
	
	ProfessorRepository professorRepository;
	PersonIService personService;
	
	@Autowired
	public ProfessorService(ProfessorRepository professorRepository, PersonIService personService) {
		super();
		this.professorRepository = professorRepository;
		this.personService = personService;
	}
	@Override
	@Transactional
	public Professor findById(Integer id) {
    	return professorRepository.findById(id).get();
	}

	@Override
	@Transactional
	public void saveProfessor(Professor professor) {
		personService.savePerson(professor.getPerson());
		professorRepository.save(professor);
	}
	@Override
	@Transactional
	public void updateProfessor(Professor professor) {
		Integer idPerson = findById(professor.getProfessorId()).getPerson().getPersonId();
		professor.getPerson().setPersonId(idPerson);
		personService.savePerson(professor.getPerson());
		saveProfessor(professor);
	}
	@Override
	@Transactional
	public void deleteProfessorById(Integer id){
		Integer idPerson = findById(id).getPerson().getPersonId();
		professorRepository.deleteById(id);
		personService.deletePersonById(idPerson);
		
	}

	@Override
	@Transactional
	public List<Professor> findAllProfessors(){
		return professorRepository.findAll();
	}
	@Override
	@Transactional
	public boolean isProfessorExist(Professor professor) {
		return findById(professor.getProfessorId()) != null;
	}
}

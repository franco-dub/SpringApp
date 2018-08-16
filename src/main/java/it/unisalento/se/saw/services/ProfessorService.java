package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.PersonIService;
import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.repo.ProfessorRepository;

@Service
public class ProfessorService implements ProfessorIService{
	
	private static final ModelMapper modelMapper = new ModelMapper();
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
	public ProfessorDto findById(Integer id) {
		Professor professor = professorRepository.findById(id).get();
    	ProfessorDto professorDto = modelMapper.map(professor, ProfessorDto.class);
    	return professorDto;
	}

	@Override
	@Transactional
	public void saveProfessor(ProfessorDto professorDto) {
		Professor professor = modelMapper.map(professorDto, Professor.class);
		personService.savePerson(professorDto.getPerson());
		professorRepository.save(professor);
	}
	@Override
	@Transactional
	public void updateProfessor(ProfessorDto professorDto) {
		Integer idPerson = findById(professorDto.getProfessorId()).getPerson().getPersonId();
		professorDto.getPerson().setPersonId(idPerson);
		personService.savePerson(professorDto.getPerson());
		saveProfessor(professorDto);
	}
	@Override
	@Transactional
	public void deleteProfessorById(Integer id){
		professorRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<ProfessorDto> findAllProfessors(){
		List<Professor> professors = professorRepository.findAll();
		Type targetListType = new TypeToken<List<ProfessorDto>>() {}.getType();
    	List<ProfessorDto> professorDtos = modelMapper.map(professors, targetListType);
        return professorDtos;
	}
}

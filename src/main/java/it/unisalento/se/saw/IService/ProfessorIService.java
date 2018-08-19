package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.PersonDto;
import it.unisalento.se.saw.dto.ProfessorDto;

public interface ProfessorIService {
	public ProfessorDto findById(Integer id);
	public void saveProfessor(ProfessorDto professorDto);
	public void updateProfessor(ProfessorDto professorDto);
	public void deleteProfessorById(Integer id);
	public List<ProfessorDto> findAllProfessors();
	public ProfessorDto findByPerson(PersonDto personDto);
}
package it.unisalento.se.saw.IService;

import java.util.List;
import java.util.Optional;

import it.unisalento.se.saw.domain.Professor;

public interface ProfessorIService {
	public Professor findById(Integer id);
	public void saveProfessor(Professor professor);
	public void updateProfessor(Professor professor);
	public void deleteProfessorById(Integer id);
	public List<Professor> findAllProfessors();
	public boolean isProfessorExist(Professor professor);
}
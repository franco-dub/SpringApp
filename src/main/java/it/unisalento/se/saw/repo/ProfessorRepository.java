package it.unisalento.se.saw.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{
	
}

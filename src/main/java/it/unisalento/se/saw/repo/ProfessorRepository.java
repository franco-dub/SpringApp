package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer>{

	//List<Professor> findByLastName(@Param("lastName") String lastName);
}

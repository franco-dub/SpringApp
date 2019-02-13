package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Module;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {
	List<Module> findAllByCourseCourseId(Integer courseId);
	Module findByProfessorProfessorId(Integer professorId);
	List<Module> findAllByProfessorProfessorId(Integer professorId);
}

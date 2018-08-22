package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{
	Student findByPerson(Person person);
	List<Student> findAllByCourseCourseId(Integer courseId);
}

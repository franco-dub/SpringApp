package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>{

	List<Person> findByLastName(@Param("lastName") String lastName);
	
	Person findByEmailAndPassword(String email, String password);
}

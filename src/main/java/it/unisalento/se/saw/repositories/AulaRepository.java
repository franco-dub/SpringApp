package it.unisalento.se.saw.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {
	
	//@Query("select a from Aula a where a.name=:name")
	//public List<Aula> getByName(@Param("name") String name);
}

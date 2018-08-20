package it.unisalento.se.saw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.domain.Secretary;

@Repository
public interface SecretaryRepository extends JpaRepository<Secretary, Integer> {
	Secretary findByPerson(Person person);
}

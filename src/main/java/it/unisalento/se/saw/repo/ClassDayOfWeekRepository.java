package it.unisalento.se.saw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.ClassDayOfWeek;

@Repository
public interface ClassDayOfWeekRepository extends JpaRepository<ClassDayOfWeek, Integer> {

}

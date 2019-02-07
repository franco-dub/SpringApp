package it.unisalento.se.saw.repo;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
	List<Calendar> findAllByDate(Date date);
	List<Calendar> findAllByModule(Module module);
}

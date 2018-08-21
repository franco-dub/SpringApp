package it.unisalento.se.saw.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.LectureCalendar;

@Repository
public interface LectureCalendarRepository extends JpaRepository<LectureCalendar, Integer> {
	List<LectureCalendar> findAllByDate(Date date);
}

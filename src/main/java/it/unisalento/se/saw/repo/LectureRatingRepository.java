package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.LectureRating;

@Repository
public interface LectureRatingRepository extends JpaRepository<LectureRating, Integer>{

	List<LectureRating> findByCalendarCalendarId(Integer calendarId);
	@Query("SELECT lr FROM LectureRating lr WHERE lr.student.studentId = :studentId "
			+ "AND lr.calendar.calendarId = :calendarId")
	LectureRating findByStudentAndLecture(@Param("studentId") Integer studentId,@Param("calendarId") Integer calendarId);
}

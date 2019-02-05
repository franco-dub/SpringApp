package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.TmRating;

@Repository
public interface TmRatingRepository extends JpaRepository<TmRating, Integer>{
	List<TmRating> findByTeachingMaterialTeachingMaterialId(Integer teachingMaterialId);
	@Query("SELECT tr FROM TmRating tr WHERE tr.student.studentId = :studentId "
			+ "AND tr.teachingMaterial.teachingMaterialId = :teachingMaterialId")
	TmRating findByStudentAndTm(
			@Param("teachingMaterialId") Integer teachingMaterialId,
			@Param("studentId") Integer studentId);
}

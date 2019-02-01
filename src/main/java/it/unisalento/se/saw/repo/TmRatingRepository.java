package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.TmRating;

@Repository
public interface TmRatingRepository extends JpaRepository<TmRating, Integer>{
	List<TmRating> findByTeachingMaterialTeachingMaterialId(Integer teachingMaterialId);
}

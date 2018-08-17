package it.unisalento.se.saw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.LectureRating;

@Repository
public interface LectureRatingRepository extends JpaRepository<LectureRating, Integer>{

}

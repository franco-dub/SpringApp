package it.unisalento.se.saw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Integer>{

}

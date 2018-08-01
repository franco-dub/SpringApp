package it.unisalento.se.saw.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.StudentExam;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Integer>{

}

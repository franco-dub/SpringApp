package it.unisalento.se.saw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.CorsoDiStudio;

@Repository
public interface CorsoDiStudioRepository extends JpaRepository<CorsoDiStudio, Integer> {

}

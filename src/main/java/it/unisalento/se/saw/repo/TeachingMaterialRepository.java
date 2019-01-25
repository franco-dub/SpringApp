package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.dto.TeachingMaterialDto;

@Repository
public interface TeachingMaterialRepository extends JpaRepository<TeachingMaterial, Integer>{
	@Query("select new it.unisalento.se.saw.dto.TeachingMaterialDto(techingMaterialId, fileName, fileType, created, "
			+ "size) from TeachingMaterial tm where tm.module.moduleId = :moduleId")
	List<TeachingMaterialDto> findFileByModuleId(@Param("moduleId") Integer moduleId);
}

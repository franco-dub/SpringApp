package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import it.unisalento.se.saw.domain.TeachingMaterial;

@Repository
public interface TeachingMaterialRepository extends JpaRepository<TeachingMaterial, Integer>{
	@Query("select tm.techingMaterialId, tm.fileName, tm.fileType, tm.created, "
			+ "tm.size from TeachingMaterial tm where tm.module.moduleId = :moduleId")
	List<TeachingMaterial> findFileByModuleId(@Param("moduleId") Integer moduleId);
}

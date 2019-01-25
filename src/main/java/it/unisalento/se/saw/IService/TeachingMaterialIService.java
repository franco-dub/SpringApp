package it.unisalento.se.saw.IService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.dto.TeachingMaterialDto;

public interface TeachingMaterialIService {
	public TeachingMaterial storeFile(Integer moduleId, MultipartFile file);
	public TeachingMaterial getFile(Integer fileId);
	public List<TeachingMaterialDto> findByModule(Integer moduleId);
	public void deleteFileById(Integer id);
}

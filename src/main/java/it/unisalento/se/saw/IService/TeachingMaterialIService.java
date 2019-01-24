package it.unisalento.se.saw.IService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import it.unisalento.se.saw.domain.TeachingMaterial;

public interface TeachingMaterialIService {
	public TeachingMaterial storeFile(Integer moduleId, MultipartFile file);
	public TeachingMaterial getFile(Integer fileId);
	public List<String> findByModule(Integer moduleId);
}

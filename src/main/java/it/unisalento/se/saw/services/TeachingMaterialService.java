package it.unisalento.se.saw.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.IService.TeachingMaterialIService;
import it.unisalento.se.saw.domain.Module;
import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.TeachingMaterialDto;
import it.unisalento.se.saw.exceptions.FileStorageException;
import it.unisalento.se.saw.exceptions.MyFileNotFoundException;
import it.unisalento.se.saw.repo.TeachingMaterialRepository;


@Service
public class TeachingMaterialService implements TeachingMaterialIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	TeachingMaterialRepository teachingMaterialRepository;
	ModuleIService moduleService;
	@Autowired
	public TeachingMaterialService(TeachingMaterialRepository teachingMaterialRepository,
			ModuleIService moduleService) {
		super();
		this.teachingMaterialRepository = teachingMaterialRepository;
		this.moduleService = moduleService;
	}
	
	public TeachingMaterial storeFile(Integer moduleId, MultipartFile file) {
		// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            ModuleDto moduleDto = moduleService.findById(moduleId);
            Module module = modelMapper.map(moduleDto, Module.class);
            TeachingMaterial dbFile = new TeachingMaterial(module, file.getBytes(), fileName, file.getContentType(), new Date(), file.getSize());
            return teachingMaterialRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
	}
	

	public TeachingMaterial getFile(Integer fileId) {
        return teachingMaterialRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
	
	
	public List<TeachingMaterialDto> findByModule(Integer moduleId){
		return teachingMaterialRepository.findFileByModuleId(moduleId);
	}
	
	@Override
	@Transactional
	public void deleteFileById(Integer id){
		teachingMaterialRepository.deleteById(id);
	}
}

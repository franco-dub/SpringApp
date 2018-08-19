package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.domain.Module;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.repo.ModuleRepository;

@Service
public class ModuleService implements ModuleIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	ModuleRepository moduleRepository;
	
	@Autowired
	public ModuleService(ModuleRepository moduleRepository) {
		super();
		this.moduleRepository = moduleRepository;
	}
	
	@Override
	@Transactional
	public ModuleDto findById(Integer id) {
		Module module = moduleRepository.findById(id).get();
		ModuleDto moduleDto = modelMapper.map(module, ModuleDto.class);
		return moduleDto;
	}

	@Override
	@Transactional
	public void saveModule(ModuleDto moduleDto) {
		Module module = modelMapper.map(moduleDto, Module.class);
		moduleRepository.save(module);
	}

	@Override
	@Transactional
	public void updateModule(ModuleDto moduleDto) {
		saveModule(moduleDto);
	}

	@Override
	@Transactional
	public void deleteModuleById(Integer id) {
		moduleRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<ModuleDto> findAllModules() {
		List<Module> modules = moduleRepository.findAll();
		Type targetListType = new TypeToken<List<ModuleDto>>() {}.getType();
		List<ModuleDto> moduleDtos = modelMapper.map(modules, targetListType);
		return moduleDtos;
	}

	@Override
	@Transactional
	public List<ModuleDto> findAllCourseSModule(Integer id) {
		List<Module> modules = moduleRepository.findAllByCourseCourseId(id);
		Type targetListType = new TypeToken<List<ModuleDto>>() {}.getType();
		List<ModuleDto> moduleDtos = modelMapper.map(modules, targetListType);
		return moduleDtos;
	}

}

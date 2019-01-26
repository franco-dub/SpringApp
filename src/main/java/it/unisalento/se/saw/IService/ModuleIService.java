package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.ModuleDto;

public interface ModuleIService {
	public ModuleDto findById(Integer id);
	public void saveModule(ModuleDto moduleDto);
	public void updateModule(ModuleDto moduleDto);
	public void deleteModuleById(Integer id);
	public List<ModuleDto> findAllModules();
	public List<ModuleDto> findAllCourseSModule(Integer id);
	public List<ModuleDto> findAllProfessorSModule(Integer id);
}

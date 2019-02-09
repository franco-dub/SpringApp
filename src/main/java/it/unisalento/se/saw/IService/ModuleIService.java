package it.unisalento.se.saw.IService;

import it.unisalento.se.saw.dto.ModuleDto;

import java.util.List;

public interface ModuleIService {
	public ModuleDto findById(Integer id);
	public ModuleDto saveModule(ModuleDto moduleDto);
	public void updateModule(ModuleDto moduleDto);
	public void deleteModuleById(Integer id);
	public List<ModuleDto> findAllModules();
	public List<ModuleDto> findAllCourseSModule(Integer id);
	public ModuleDto findByProfessorProfessorId(Integer id);
	public List<ModuleDto> findAllCourseSModulePerYear(Integer id, int year);
	public List<ModuleDto> findAllProfessorSModule(Integer id);
}

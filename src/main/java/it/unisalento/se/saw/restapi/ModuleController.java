package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.naming.Binding;
import javax.validation.Valid;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController

@RequestMapping(path = "/module")
public class ModuleController {

	private ModuleIService moduleService;

	@Autowired
	public ModuleController(ModuleIService moduleService) {
		super();
		this.moduleService = moduleService;
	}
	
	// -------------------Create a Module-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createModule(@Valid @RequestBody ModuleDto moduleDto, BindingResult brs) {
    	if(!brs.hasErrors()) {
            return new ResponseEntity<>(moduleService.saveModule(moduleDto), HttpStatus.CREATED);
    	} else{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Module. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Modules--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllModules() {
    	List<ModuleDto> moduleDtos = moduleService.findAllModules();
    	if (moduleDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<ModuleDto>>(moduleDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Module------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getModule(@PathVariable("id") int id) {
    	try {
    		ModuleDto moduleDto = moduleService.findById(id);
    		return new ResponseEntity<ModuleDto>(moduleDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Module with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Update a Module ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateModule(@PathVariable("id") int id, 
    		@Valid @RequestBody ModuleDto moduleDto, BindingResult brs) {
    	if(!brs.hasErrors()) {
    		if(moduleService.findById(id) != null) {
    			moduleDto.setModuleId(id);
    			moduleService.updateModule(moduleDto);
                return new ResponseEntity<>(moduleDto, HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Module. Validation error!"),
        				HttpStatus.NOT_FOUND);
    		}
    	} else {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Module with id " 
            		+ id + " not found."), HttpStatus.BAD_REQUEST);
    	}
    }
    
    //------------------- Delete a Module --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteModule(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Module with id " + id);
        if(moduleService.findById(id) != null) {
        	moduleService.deleteModuleById(id);
            return new ResponseEntity<ModuleDto>(HttpStatus.NO_CONTENT);
        }else {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Module with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
    
//-------------------Retrieve All Course's Modules--------------------------------------------------------
    
    @RequestMapping(value = "/findAll/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCourseSModules(@PathVariable("id") Integer id) {
    	List<ModuleDto> moduleDtos = moduleService.findAllCourseSModule(id);
    	if (moduleDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<>(moduleDtos, HttpStatus.OK);
    }
    
//-------------------Retrieve All Professor's Modules--------------------------------------------------------
    
    @RequestMapping(value = "/findByProf/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllProfessorSModules(@PathVariable("id") Integer id) {
    	List<ModuleDto> moduleDtos = moduleService.findAllProfessorSModule(id);
    	if (moduleDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NOT_FOUND);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<>(moduleDtos, HttpStatus.OK);
    }
    
//-------------------Retrieve All Course's Modules per year--------------------------------------------------------
    
    @RequestMapping(value = "/findAllCoursePerYear/{id}/{year}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCourseSModulesPerYear(@PathVariable("id") Integer id,
    		@PathVariable("year") int year) {
    	List<ModuleDto> moduleDtos = moduleService.findAllCourseSModule(id);
    	if (moduleDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
    	}
	    List<ModuleDto> modules = new ArrayList<>();
	    for(ModuleDto mod : moduleDtos){
		    if(mod.getYear() == year){
			    modules.add(mod);
		    }
	    }
	    return new ResponseEntity<>(modules, HttpStatus.OK);

    }
}

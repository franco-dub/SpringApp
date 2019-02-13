package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.dto.ModuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/professor")
public class ProfessorController {
	
	private ProfessorIService professorService;
    private ModuleIService moduleService;

	@Autowired
	protected ProfessorController(ProfessorIService professorService, ModuleIService moduleService) {
		super();
		this.professorService = professorService;
		this.moduleService = moduleService;
	}


	// -------------------Create a Professor-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createProfessor(@Valid @RequestBody ProfessorDto professorDto, BindingResult brs) {
    	if(!brs.hasErrors()) {
            return new ResponseEntity<>(professorService.saveProfessor(professorDto), HttpStatus.CREATED);
    	} else {
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Professor. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Professors--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllProfessors() {
    	List<ProfessorDto> professorDtos = professorService.findAllProfessors();
    	if (professorDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
    	}
        return new ResponseEntity<>(professorDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Professor------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProfessor(@PathVariable("id") int id) {
		ProfessorDto professorDto = professorService.findById(id);
    	if(professorDto != null){
    	    return new ResponseEntity<>(professorDto, HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(new CustomErrorType("Professor with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Professor ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfessor(@PathVariable("id") int id, 
    		@Valid @RequestBody ProfessorDto professorDto, BindingResult brs) {
    	if(!brs.hasErrors()) {

    		if(professorService.findById(id) != null){
    			professorDto.setProfessorId(id);
    			professorService.updateProfessor(professorDto);
                return new ResponseEntity<>(professorDto, HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Professor. Validation error!"),
        				HttpStatus.NOT_FOUND);
    		}
    	} else {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Professor with id " 
            		+ id + " not found."), HttpStatus.BAD_REQUEST);
    	}
    }
    
//------------------- Delete a Professor --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Professor with id " + id);
        if(professorService.findById(id) != null) {
        	professorService.deleteProfessorById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Professor with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }

//-----------------------Find Professor Module---------------------------------------------

    @RequestMapping(value = "/findModuleByProfessorId/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findModuleByProfessorId(@PathVariable("id") Integer id){
		ModuleDto moduleDto = moduleService.findByProfessorProfessorId(id);
		if(moduleDto != null){
            return new ResponseEntity<>(moduleDto, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(new CustomErrorType("Unable to find module! Professor with id " + id
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }

}

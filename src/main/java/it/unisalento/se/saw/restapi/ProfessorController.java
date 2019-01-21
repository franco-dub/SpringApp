package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/professor")
public class ProfessorController {
	
	ProfessorIService professorService;
	
	@Autowired
	protected ProfessorController(ProfessorIService professorService) {
		super();
		this.professorService = professorService;
	}


	// -------------------Create a Professor-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createProfessor(@Valid @RequestBody ProfessorDto professorDto) {
    	try {
    		professorService.saveProfessor(professorDto);
            return new ResponseEntity<ProfessorDto>(professorDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
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
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<ProfessorDto>>(professorDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Professor------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProfessor(@PathVariable("id") int id) {
    	try {
    		ProfessorDto professorDto = professorService.findById(id);
    		return new ResponseEntity<ProfessorDto>(professorDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Professor with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Professor ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfessor(@PathVariable("id") int id, 
    		@Valid @RequestBody ProfessorDto professorDto) {
    	try {
    		professorService.findById(id);
    		try {
    			professorDto.setProfessorId(id);
    			professorService.updateProfessor(professorDto);
                return new ResponseEntity<ProfessorDto>(professorDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Professor. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Professor with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Professor --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteProfessor(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Professor with id " + id);
        try {
        	professorService.findById(id);
        	professorService.deleteProfessorById(id);
            return new ResponseEntity<Professor>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Professor with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

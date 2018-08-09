package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import it.unisalento.se.saw.IService.ProfessorIService;
import it.unisalento.se.saw.domain.Professor;
import it.unisalento.se.saw.dto.ProfessorDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import it.unisalento.se.saw.util.Dto;

@RestController
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
    public ResponseEntity<?> createProfessor(@Valid @RequestBody 
    		@Dto(ProfessorDto.class) Professor professor, UriComponentsBuilder ucBuilder) {
    	try {
    		professorService.saveProfessor(professor);
    		
    		HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/{id}")
            		.buildAndExpand(professor.getProfessorId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Professor. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Professors--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllProfessors() {
    	List<Professor> professors = professorService.findAllProfessors();
    	if (professors.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<Professor>>(professors, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Professor------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProfessor(@PathVariable("id") int id) {
    	try {
    		Professor professor = professorService.findById(id);
    		return new ResponseEntity<Professor>(professor, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Professor with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Professor ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateProfessor(@PathVariable("id") int id, 
    		@Valid @RequestBody @Dto(ProfessorDto.class)Professor professor) {
    	try {
    		professorService.findById(id);
    		try {
    			professor.setProfessorId(id);
    			professorService.updateProfessor(professor);
                return new ResponseEntity<Professor>(professor, HttpStatus.OK);
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

package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.domain.Secretary;
import it.unisalento.se.saw.dto.SecretaryDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/secretary")
public class SecretaryController {

	SecretaryIService secretaryService;

	@Autowired
	public SecretaryController(SecretaryIService secretaryService) {
		super();
		this.secretaryService = secretaryService;
	}
	
	// -------------------Create a Secretary-------------------------------------------

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<?> createSecretary(@Valid @RequestBody 
			SecretaryDto secretaryDto, BindingResult brs) {
		if (!brs.hasErrors()) {
			secretaryService.saveSecretary(secretaryDto);
			return new ResponseEntity<SecretaryDto>(secretaryDto, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(new CustomErrorType("Unable to create new Secretary. Validation error!"),
				HttpStatus.BAD_REQUEST);
	}
    
    //-------------------Retrieve All Secretaries--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllSecretaries() {
    	List<SecretaryDto> secretaryDtos = secretaryService.findAllSecretaries();
    	if (secretaryDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<SecretaryDto>>(secretaryDtos, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single Secretary------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getSecretary(@PathVariable("id") int id) {
    	try {
    		SecretaryDto secretaryDto = secretaryService.findById(id);
    		return new ResponseEntity<SecretaryDto>(secretaryDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Secretary with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Update a Secretary ------------------------------------------------

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSecretary(@PathVariable("id") int id, 
    		@Valid @RequestBody SecretaryDto secretaryDto, BindingResult brs) {
    	if (!brs.hasErrors()) {
    		try {
    			secretaryService.findById(id);
    		} catch( Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to upate. Secretary with id " 
    					+ id + " not found."), HttpStatus.NOT_FOUND);
    		}
    		secretaryDto.setSecretaryId(id);
    		secretaryService.updateSecretary(secretaryDto);
    		return new ResponseEntity<SecretaryDto>(secretaryDto, HttpStatus.OK);
    	}
    	return new ResponseEntity<>(new CustomErrorType("Unable to create new Secretary. Validation error!"),
    			HttpStatus.BAD_REQUEST);
    }
    
    //------------------- Delete a Secretary --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSecretary(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Secretary with id " + id);
        try {
        	secretaryService.findById(id);
        	secretaryService.deleteSecretaryById(id);
            return new ResponseEntity<Secretary>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Secretary with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

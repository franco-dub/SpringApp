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

import it.unisalento.se.saw.IService.SecretaryIService;
import it.unisalento.se.saw.domain.Secretary;
import it.unisalento.se.saw.dto.SecretaryDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
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
    		SecretaryDto secretaryDto, UriComponentsBuilder ucBuilder) {
    	try {
    		secretaryService.saveSecretary(secretaryDto);
    		
    		HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/{id}")
            		.buildAndExpand(secretaryDto.getSecretaryId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Secretary. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
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
    		@Valid @RequestBody SecretaryDto secretaryDto) {
    	try {
    		secretaryService.findById(id);
    		try {
    			secretaryDto.setSecretaryId(id);
    			secretaryService.updateSecretary(secretaryDto);
                return new ResponseEntity<SecretaryDto>(secretaryDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Secretary. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Secretary with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
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

package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.EquipmentIService;
import it.unisalento.se.saw.dto.EquipmentDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@RequestMapping(path = "/equipment")
public class EquipmentController {

	EquipmentIService equipmentService;
	@Autowired
	public EquipmentController(EquipmentIService equipmentService) {
		super();
		this.equipmentService = equipmentService;
	}
	
// -------------------Create a Equipment-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createEquipment(@Valid @RequestBody EquipmentDto equipmentDto) {
    	try {
    		equipmentService.saveEquipment(equipmentDto);
            return new ResponseEntity<EquipmentDto>(equipmentDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Equipment. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Equipments--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllEquipments() {
    	List<EquipmentDto> equipmentDtos = equipmentService.findAllEquipments();
    	if (equipmentDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<EquipmentDto>>(equipmentDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Equipment------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEquipment(@PathVariable("id") int id) {
    	try {
    		EquipmentDto equipmentDto = equipmentService.findById(id);
    		return new ResponseEntity<EquipmentDto>(equipmentDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Equipment with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Equipment ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateEquipment(@PathVariable("id") int id, 
    		@Valid @RequestBody EquipmentDto equipmentDto) {
    	try {
    		equipmentService.findById(id);
    		try {
    			equipmentDto.setEquipmentId(id);
    			equipmentService.updateEquipment(equipmentDto);
                return new ResponseEntity<EquipmentDto>(equipmentDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Equipment. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Equipment with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Equipment --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEquipment(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Equipment with id " + id);
        try {
        	equipmentService.findById(id);
        	equipmentService.deleteEquipmentById(id);
            return new ResponseEntity<EquipmentDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Equipment with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

package it.unisalento.se.saw.restapi;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.RoomEquipmentIService;
import it.unisalento.se.saw.dto.RoomEquipmentDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
@RestController
@CrossOrigin
@RequestMapping(path = "/roomEquipment")
public class RoomEquipmentController {

	RoomEquipmentIService roomEquipmentService;
	@Autowired
	public RoomEquipmentController(RoomEquipmentIService roomEquipmentService) {
		super();
		this.roomEquipmentService = roomEquipmentService;
	}
	
// -------------------Create a RoomEquipment-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createRoomEquipment(@Valid @RequestBody List<RoomEquipmentDto> roomEquipmentDtos) {
    	try {
    		for(RoomEquipmentDto roomEquipmentDto: roomEquipmentDtos) {
    			roomEquipmentService.saveRoomEquipment(roomEquipmentDto);
    		}
            return new ResponseEntity<List<RoomEquipmentDto>>(roomEquipmentDtos, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new RoomEquipment. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All RoomEquipments--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllRoomEquipments() {
    	List<RoomEquipmentDto> roomEquipmentDtos = roomEquipmentService.findAllRoomEquipments();
    	if (roomEquipmentDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<RoomEquipmentDto>>(roomEquipmentDtos, HttpStatus.OK);
    }
    
//---------------Retrieve All RoomEquipments of a Room----------------------------------------
    
    @RequestMapping(value = "/findByRoomId/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findAllRoomEquipmentByRoomId(@Valid @PathVariable("id") Integer id){
    	try {
    		List<RoomEquipmentDto> roomEquipmentDtos = roomEquipmentService.findRoomEquipmentsByroomId(id);
    		return new ResponseEntity<List<RoomEquipmentDto>>(roomEquipmentDtos, HttpStatus.OK);
    	} catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("RoomEquipment with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
    	}
    }
    
    
// -------------------Retrieve Single RoomEquipment------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoomEquipment(@PathVariable("id") int id) {
    	try {
    		RoomEquipmentDto roomEquipmentDto = roomEquipmentService.findById(id);
    		return new ResponseEntity<RoomEquipmentDto>(roomEquipmentDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("RoomEquipment with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a RoomEquipment ------------------------------------------------
    
    @RequestMapping(value = "/updateById/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRoomEquipment(@PathVariable("id") int id, 
    		@Valid @RequestBody RoomEquipmentDto roomEquipmentDto) {
    	try {
    		roomEquipmentService.findById(id);
    		try {
    			roomEquipmentDto.setRoomEquipmentId(id);
    			roomEquipmentService.updateRoomEquipment(roomEquipmentDto);
                return new ResponseEntity<RoomEquipmentDto>(roomEquipmentDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new RoomEquipment. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. RoomEquipment with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
    
//-------------------Update RoomEquipment of a Room-------------------------------------------
    
    
    @RequestMapping(value = "/updateByRoom", method = RequestMethod.POST)
    public ResponseEntity<?> updateRoomEquipmentByRoom(@Valid @RequestBody List<RoomEquipmentDto> roomEquipmentDtos){
    	System.out.println(roomEquipmentDtos);
    	try {
    		List<RoomEquipmentDto> newRoomEquipmentDtos = new ArrayList<>();
    		for(RoomEquipmentDto roomEquipmentDto: roomEquipmentDtos) {
    			newRoomEquipmentDtos.add(roomEquipmentService.updateRoomEquipment(roomEquipmentDto));
    		}
    		return new ResponseEntity<List<RoomEquipmentDto>>(newRoomEquipmentDtos, HttpStatus.OK);
    	}catch(Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new RoomEquipment. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
    
    
//------------------- Delete a RoomEquipment --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoomEquipment(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting RoomEquipment with id " + id);
        try {
        	roomEquipmentService.findById(id);
        	roomEquipmentService.deleteRoomEquipmentById(id);
            return new ResponseEntity<RoomEquipmentDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! RoomEquipment with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
    
//-------------------Retrieve All Room's Equipments--------------------------------------------------------
    
    @RequestMapping(value = "/findAllRoomEquipment/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllRoomSEquipments(@PathVariable("id") Integer id) {
    	List<RoomEquipmentDto> roomEquipmentDtos = roomEquipmentService.findAllRoomSEquipments(id);
    	if (roomEquipmentDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<RoomEquipmentDto>>(roomEquipmentDtos, HttpStatus.OK);
    }
    
//-------------------Retrieve All Rooms Where Equipment--------------------------------------------------------
    
    @RequestMapping(value = "/findAllRoomWhereEquipment/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllRoomsWhereEquipment(@PathVariable("id") Integer id) {
    	List<RoomEquipmentDto> roomEquipmentDtos = roomEquipmentService.findAllRoomsWhereEquipments(id);
    	if (roomEquipmentDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<RoomEquipmentDto>>(roomEquipmentDtos, HttpStatus.OK);
    }
}

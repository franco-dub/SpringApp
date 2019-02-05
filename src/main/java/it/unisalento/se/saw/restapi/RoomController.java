package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/room")
public class RoomController {

	RoomIService roomService;
	@Autowired
	public RoomController(RoomIService roomService) {
		super();
		this.roomService = roomService;
	}
	
// -------------------Create a Room-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDto roomDto) {
    	try {
            return new ResponseEntity<RoomDto>(roomService.saveRoom(roomDto), HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Room. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Rooms--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllRooms() {
    	List<RoomDto> roomDtos = roomService.findAllRooms();
    	if (roomDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<RoomDto>>(roomDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Room------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRoom(@PathVariable("id") Integer id) {
    	try {
    		RoomDto roomDto = roomService.findById(id);
    		return new ResponseEntity<RoomDto>(roomDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Room with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Room ------------------------------------------------
    
    @RequestMapping(value = "/updateById/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> updateRoom(@PathVariable("id") Integer id, 
    		@Valid @RequestBody RoomDto roomDto) {
    	try {
    		System.out.println(id + " " + roomDto);
    		roomService.findById(id);
    		try {
    			roomDto.setRoomId(id);
    			roomService.updateRoom(roomDto);
                return new ResponseEntity<RoomDto>(roomDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Room. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Room with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Room --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Room with id " + id);
        try {
        	roomService.findById(id);
        	roomService.deleteRoomById(id);
            return new ResponseEntity<RoomDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Room with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

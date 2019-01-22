package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/scheduling")
public class SchedulingController {

	SchedulingIService schedulingService;
	@Autowired
	public SchedulingController(SchedulingIService schedulingService) {
		super();
		this.schedulingService = schedulingService;
	}
	
	@RequestMapping(value = "/findFreeRooms", method = RequestMethod.POST)
    public ResponseEntity<?> findFreeRooms(@Valid @RequestBody CalendarDto lectureCalendarDto) {
    	try {
    		List<RoomDto> freeRoomDtos = schedulingService.findFreeRooms(lectureCalendarDto);
            return new ResponseEntity<List<RoomDto>>(freeRoomDtos, HttpStatus.OK);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType(e.toString()),
    				HttpStatus.BAD_REQUEST);
    	}
    }
	
	// -------------------Create all semester's Calendars-------------------------------------------

    @RequestMapping(value = "/addLectures", method = RequestMethod.POST)
    public ResponseEntity<?> createCalendars(@Valid @RequestBody CalendarDto calendarDto) {
    	try {
    		schedulingService.saveAllCalendars(calendarDto);
            return new ResponseEntity<CalendarDto>(calendarDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType(e.toString()), HttpStatus.BAD_REQUEST);
    	}
    }
}

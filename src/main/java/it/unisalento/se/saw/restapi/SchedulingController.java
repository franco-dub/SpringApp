package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> findFreeRooms(@Valid @RequestBody CalendarDto lectureCalendarDto, BindingResult brs) {
    	if(!brs.hasErrors()){
    		System.out.println(lectureCalendarDto.getCalendarDate().getEndDate() + " " + lectureCalendarDto.getCalendarDate().getStartDate());
    		List<RoomDto> roomDtos = schedulingService.findFreeRooms(lectureCalendarDto);
    		System.out.println(roomDtos);
    		if(!roomDtos.isEmpty()){
			    return new ResponseEntity<>(roomDtos, HttpStatus.OK);
		    }else{
			    return new ResponseEntity<>(new CustomErrorType("No free rooms found"),
					    HttpStatus.NOT_FOUND);
		    }
    	} else{
    		return new ResponseEntity<>(new CustomErrorType("Bad Request. Missing arguments"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
	
	// -------------------Create all semester's Calendars-------------------------------------------

    @RequestMapping(value = "/addLectures", method = RequestMethod.POST)
    public ResponseEntity<?> createCalendars(@Valid @RequestBody CalendarDto calendarDto, BindingResult brs) {
    	if(!brs.hasErrors()){
    		schedulingService.saveAllCalendars(calendarDto);
            return new ResponseEntity<>(calendarDto, HttpStatus.CREATED);
    	} else{
    		return new ResponseEntity<>(new CustomErrorType("Bad request. Missing arguments"), HttpStatus.BAD_REQUEST);
    	}
    }
}


package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/calendar")
public class CalendarController {

	CalendarIService calendarService;
	@Autowired
	public CalendarController(CalendarIService calendarService) {
		super();
		this.calendarService = calendarService;
	}
	
// -------------------Create a Calendar-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createCalendar(@Valid @RequestBody CalendarDto calendarDto) {
    	try {
    		calendarService.saveCalendar(calendarDto);
            return new ResponseEntity<CalendarDto>(calendarDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Calendar. " + e.toString()),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Calendars--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCalendars() {
    	List<CalendarDto> calendarDtos = calendarService.findAllCalendars();
    	if (calendarDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<CalendarDto>>(calendarDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Calendar------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCalendar(@PathVariable("id") int id) {
    	try {
    		CalendarDto calendarDto = calendarService.findById(id);
    		return new ResponseEntity<CalendarDto>(calendarDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Calendar with id " + id 
                    + " not found ." + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Calendar ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCalendar(@PathVariable("id") int id, 
    		@Valid @RequestBody CalendarDto calendarDto) {
    	try {
    		calendarService.findById(id);
    		try {
    			calendarDto.setCalendarId(id);
    			calendarService.updateCalendar(calendarDto);
                return new ResponseEntity<CalendarDto>(calendarDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Calendar. " + e.toString()),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Calendar with id " 
            		+ id + " not found. " + e.toString()), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Calendar --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCalendar(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Calendar with id " + id);
        try {
        	calendarService.findById(id);
        	calendarService.deleteCalendarById(id);
            return new ResponseEntity<CalendarDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Calendar with id " + id 
                    + " not found. " + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
//-------------------Retrieve All Calendars By Module--------------------------------------------------------
    
    @RequestMapping(value = "/findByModuleId/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCalendarsByModule(@PathVariable("id") Integer id) {
    	List<CalendarDto> calendarDtos = calendarService.findAllCalendarByModule(id);
    	if (calendarDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<CalendarDto>>(calendarDtos, HttpStatus.OK);
    }
}

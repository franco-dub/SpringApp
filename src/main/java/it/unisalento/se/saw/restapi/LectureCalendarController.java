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

import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@RequestMapping(path = "/lectureCalendar")
public class LectureCalendarController {

	CalendarIService lectureCalendarService;
	@Autowired
	public LectureCalendarController(CalendarIService lectureCalendarService) {
		super();
		this.lectureCalendarService = lectureCalendarService;
	}
	
// -------------------Create a Lecture-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createLecture(@Valid @RequestBody CalendarDto lectureCalendarDto) {
    	try {
    		lectureCalendarService.saveLecture(lectureCalendarDto);
            return new ResponseEntity<CalendarDto>(lectureCalendarDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Lecture. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All Lectures--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllLectures() {
    	List<CalendarDto> lectureDtos = lectureCalendarService.findAllLectures();
    	if (lectureDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<CalendarDto>>(lectureDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single Lecture------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLecture(@PathVariable("id") int id) {
    	try {
    		CalendarDto lectureCalendarDto = lectureCalendarService.findById(id);
    		return new ResponseEntity<CalendarDto>(lectureCalendarDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Lecture with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
// ------------------- Update a Lecture ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLecture(@PathVariable("id") int id, 
    		@Valid @RequestBody CalendarDto lectureCalendarDto) {
    	try {
    		lectureCalendarService.findById(id);
    		try {
    			lectureCalendarDto.setLectureCalendarId(id);
    			lectureCalendarService.updateLecture(lectureCalendarDto);
                return new ResponseEntity<CalendarDto>(lectureCalendarDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Lecture. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to update. Lecture with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
//------------------- Delete a Lecture --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLecture(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Lecture with id " + id);
        try {
        	lectureCalendarService.findById(id);
        	lectureCalendarService.deleteLectureById(id);
            return new ResponseEntity<CalendarDto>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Lecture with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

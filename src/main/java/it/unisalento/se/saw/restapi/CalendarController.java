package it.unisalento.se.saw.restapi;


import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/calendar")
public class CalendarController {

	private CalendarIService calendarService;
	private ModuleIService moduleService;

	@Autowired
	public CalendarController(CalendarIService calendarService, ModuleIService moduleService) {
		super();
		this.calendarService = calendarService;
		this.moduleService = moduleService;
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

  //--------------------- Get Student Calendar ------------------------------------
    @PostMapping(value = "getStudentCalendar", consumes = "application/json")
	public ResponseEntity<?> getStudentCalendar(@Valid @RequestBody StudentDto studentDto){
		try{
			List<ModuleDto> modules = moduleService.findAllCourseSModule(studentDto.getCourse().getCourseId());
			modules.forEach(module->{
				if(module.getYear() != studentDto.getYear()){
					System.out.println(modules);
					modules.remove(module);
					System.out.println(modules);

				}
			});

			return new ResponseEntity<>(new CustomErrorType("Unable to find student! " + studentDto +
					" not found. "), HttpStatus.NOT_FOUND);
		}catch(Exception e){
			return new ResponseEntity<>(new CustomErrorType("Unable to find student! " + studentDto +
					" not found. " + e.toString()), HttpStatus.NOT_FOUND);
		}
    }


  
  //---------------------------- Get Module Calendar --------------------------------
    @PostMapping(value = "getModuleCalendar", consumes = "application/json")
	public ResponseEntity<?> getModuleCalendar(@Valid @RequestBody ModuleDto moduleDto){
	    try{

		    return new ResponseEntity<>(calendarService.findCalendarByModule(moduleDto), HttpStatus.OK);

	    }catch(Exception e){
		    return new ResponseEntity<>(new CustomErrorType("Unable to find student! " +
				    " not found. " + e.toString()), HttpStatus.NOT_FOUND);
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
    
    //------------------------------- Get all professor calendar ---------------
    @RequestMapping(value = "/findByProfessorAndDate/{id}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllProfessorsCalendar(@PathVariable("id") Integer id, @PathVariable("date") String date) {
    
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate lDate = LocalDate.parse(date, formatter);
		Date dd = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	
		List<ModuleDto> modules = moduleService.findAllProfessorSModule(id);
    	if (modules.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        //return new ResponseEntity<List<ModuleDto>>(modules, HttpStatus.OK);
    	List<CalendarDto> cal = new ArrayList<>();
    	for (ModuleDto module: modules) {
    		List<CalendarDto> calendarDtos = calendarService.findAllCalendarByModuleAndDate(module.getModuleId(), dd);
    		cal.addAll(calendarDtos);
    	}

    	return new ResponseEntity<List<CalendarDto>>(cal, HttpStatus.OK);
    }
    
    
    //---------------- Get all student calendar ---------------------
    @RequestMapping(value = "/findByStudentAndDate/{id}/{year}/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> listAllStudentCalendar(@PathVariable("id") Integer id,
    		@PathVariable("year") Integer year,
    		@PathVariable("date") String date) {
    
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate lDate = LocalDate.parse(date, formatter);
		Date dd = Date.from(lDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    	
		List<ModuleDto> modules = moduleService.findAllCourseSModulePerYear(id, year);
    	if (modules.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        //return new ResponseEntity<List<ModuleDto>>(modules, HttpStatus.OK);
    	List<CalendarDto> cal = new ArrayList<>();
    	for (ModuleDto module: modules) {
    		List<CalendarDto> calendarDtos = calendarService.findAllCalendarByModuleAndDate(module.getModuleId(), dd);
    		cal.addAll(calendarDtos);
    	}

    	return new ResponseEntity<List<CalendarDto>>(cal, HttpStatus.OK);
    }
}

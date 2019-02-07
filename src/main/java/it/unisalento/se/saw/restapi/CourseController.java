package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@RestController
@CrossOrigin
@RequestMapping(path = "/course")
public class CourseController {
	
	CourseIService courseService;
	
	@Autowired
	public CourseController(CourseIService courseService) {
		super();
		this.courseService = courseService;
	}
	
	// -------------------Create a Course-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
    	try {
    		courseService.saveCourse(courseDto);
            return new ResponseEntity<CourseDto>(courseDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Course. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
	
    //-------------------Retrieve All Courses--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCourses() {
    	List<CourseDto> courseDtos = courseService.findAllCourses();
    	if (courseDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<CourseDto>>(courseDtos, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single Course------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCourse(@PathVariable("id") int id) {
    	try {
    		CourseDto courseDto = courseService.findById(id);
    		return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Course with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Update a Course ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCourse(@PathVariable("id") int id, 
    		@Valid @RequestBody CourseDto courseDto) {
    	try {
    		courseService.findById(id);
    		try {
    			courseDto.setCourseId(id);
    			courseService.updateCourse(courseDto);
                return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    		} catch (Exception e) {
    			return new ResponseEntity<>(new CustomErrorType("Unable to create new Course. Validation error!"),
        				HttpStatus.BAD_REQUEST);
    		}
    	} catch( Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Unable to upate. Course with id " 
            		+ id + " not found."), HttpStatus.NOT_FOUND);
    	}
    }
    
    //------------------- Delete a Course --------------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting Course with id " + id);
        try {
        	courseService.findById(id);
        	courseService.deleteCourseById(id);
            return new ResponseEntity<Course>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! Course with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}

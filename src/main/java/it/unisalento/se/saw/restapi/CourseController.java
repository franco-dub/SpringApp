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

import it.unisalento.se.saw.IService.CourseIService;
import it.unisalento.se.saw.domain.Course;
import it.unisalento.se.saw.dto.CourseDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import it.unisalento.se.saw.util.Dto;

@RestController
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
    public ResponseEntity<?> createCourse(@Valid @RequestBody 
    		@Dto(CourseDto.class) Course course, UriComponentsBuilder ucBuilder) {
    	try {
    		courseService.saveCourse(course);
    		
    		HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/{id}")
            		.buildAndExpand(course.getCourseId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new Course. Validation error!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
	
    //-------------------Retrieve All Courses--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllCourses() {
    	List<Course> courses = courseService.findAllCourses();
    	if (courses.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
    }
    
    // -------------------Retrieve Single Course------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCourse(@PathVariable("id") int id) {
    	try {
    		Course course = courseService.findById(id);
    		return new ResponseEntity<Course>(course, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("Course with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
    // ------------------- Update a Course ------------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCourse(@PathVariable("id") int id, 
    		@Valid @RequestBody @Dto(CourseDto.class)Course course) {
    	try {
    		courseService.findById(id);
    		try {
    			course.setCourseId(id);
    			courseService.updateCourse(course);
                return new ResponseEntity<Course>(course, HttpStatus.OK);
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

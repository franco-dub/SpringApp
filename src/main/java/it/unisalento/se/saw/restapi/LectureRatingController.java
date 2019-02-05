package it.unisalento.se.saw.restapi;

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

import it.unisalento.se.saw.IService.LectureRatingIService;
import it.unisalento.se.saw.dto.LectureRatingDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@CrossOrigin
@RestController
@RequestMapping(path = "/lectureRating")
public class LectureRatingController {

	LectureRatingIService lectureRatingService;

	@Autowired
	public LectureRatingController(LectureRatingIService lectureRatingService) {
		super();
		this.lectureRatingService = lectureRatingService;
	}
	
	// -------------------Create a LectureRating-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createLectureRating(@Valid @RequestBody LectureRatingDto lectureRatingDto) {
    	try {
    		lectureRatingService.saveLectureRating(lectureRatingDto);
            return new ResponseEntity<LectureRatingDto>(lectureRatingDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new LectureRating. " + e.toString()),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All LectureRating--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllLectureRatings() {
    	List<LectureRatingDto> lectureRatingDtos = lectureRatingService.findAll();
    	if (lectureRatingDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<LectureRatingDto>>(lectureRatingDtos, HttpStatus.OK);
    }
	
// -------------------Retrieve Single LectureRating------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRating(@PathVariable("id") int id) {
    	try {
    		LectureRatingDto lectureRatingDto = lectureRatingService.findById(id);
    		return new ResponseEntity<LectureRatingDto>(lectureRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("LectureRating with id " + id 
                    + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
 // -------------------Retrieve LectureRating by Lecture------------------------------------------

    @RequestMapping(value = "/findByModule/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRatingByLecture(@PathVariable("id") int id) {
    	try {
    		List<LectureRatingDto> lectureRatingDtos = lectureRatingService.findAllBylecture(id);
    		if (lectureRatingDtos.isEmpty()) {
        		return new ResponseEntity<>(new CustomErrorType("List empty."),
            			HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
        		//NO_CONTENT doesn't print json error
        	}
            return new ResponseEntity<List<LectureRatingDto>>(lectureRatingDtos, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("LectureRating with id " + id 
                    + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
// -------------------Retrieve LectureRating By Student and Lecture-----------------------
    
    @RequestMapping(value = "findByStudentAndLecture/{studentId}/{calendarId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRatingByStudentAndLecture(
    		@PathVariable("studentId") int studentId, @PathVariable("calendarId") int calendarId) {
    	try {
    		LectureRatingDto lectureRatingDto = lectureRatingService.findByStudentIdAndLectureId(studentId, calendarId);
    		return new ResponseEntity<LectureRatingDto>(lectureRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<LectureRatingDto>(new LectureRatingDto(), HttpStatus.NO_CONTENT);
//("TmRating with studentId " + studentId 
//                    + " and teachingMaterialId " + teachingMaterialId + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
    
}

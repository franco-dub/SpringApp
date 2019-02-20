package it.unisalento.se.saw.restapi;

import it.unisalento.se.saw.IService.LectureRatingIService;
import it.unisalento.se.saw.dto.LectureRatingDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/lectureRating")
public class LectureRatingController {

	private LectureRatingIService lectureRatingService;

	@Autowired
	public LectureRatingController(LectureRatingIService lectureRatingService) {
		super();
		this.lectureRatingService = lectureRatingService;
	}
	
	// -------------------Create a LectureRating-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createLectureRating(@Valid @RequestBody LectureRatingDto lectureRatingDto, BindingResult brs) {
    	if(!brs.hasErrors()) {
    		lectureRatingService.saveLectureRating(lectureRatingDto);
            return new ResponseEntity<>(lectureRatingDto, HttpStatus.CREATED);
    	} else{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new LectureRating."),
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
    	}
        return new ResponseEntity<>(lectureRatingDtos, HttpStatus.OK);
    }
	
// -------------------Retrieve Single LectureRating------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRating(@PathVariable("id") int id) {
    		LectureRatingDto lectureRatingDto = lectureRatingService.findById(id);
    		if(lectureRatingDto != null){
			    return new ResponseEntity<>(lectureRatingDto, HttpStatus.OK);
		    }else {
    		return new ResponseEntity<>(new CustomErrorType("LectureRating with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
    }
    
 // -------------------Retrieve LectureRating by Lecture------------------------------------------

    @RequestMapping(value = "/findByModule/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRatingByLecture(@PathVariable("id") int id) {
        List<LectureRatingDto> lectureRatingDtos = lectureRatingService.findAllBylecture(id);
        if (lectureRatingDtos.isEmpty()) {
		    return new ResponseEntity<>(new CustomErrorType("LectureRating with id " + id
				    + " not found"), HttpStatus.NO_CONTENT);
        }else{
	        return new ResponseEntity<>(lectureRatingDtos, HttpStatus.OK);
        }
    }
    
// -------------------Retrieve LectureRating By Student and Lecture-----------------------
    
    @RequestMapping(value = "/findByStudentAndLecture/{studentId}/{calendarId}", method = RequestMethod.GET)
    public ResponseEntity<?> getLectureRatingByStudentAndLecture(
    		@PathVariable("studentId") int studentId, @PathVariable("calendarId") int calendarId) {
<<<<<<< HEAD
    	try {
   		LectureRatingDto lectureRatingDto = lectureRatingService.findByStudentIdAndLectureId(studentId, calendarId);
    		return new ResponseEntity<LectureRatingDto>(lectureRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<LectureRatingDto>( new LectureRatingDto(), HttpStatus.NO_CONTENT);
=======
   		try {
    	LectureRatingDto lectureRatingDto = lectureRatingService.findByStudentIdAndLectureId(studentId, calendarId);
    		return new ResponseEntity<>(lectureRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>("That Lecture rating is no available for this student ", HttpStatus.NO_CONTENT);
>>>>>>> master
        }
    }
    
    
}

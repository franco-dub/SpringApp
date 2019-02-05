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

import it.unisalento.se.saw.IService.TmRatingIService;
import it.unisalento.se.saw.dto.TmRatingDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@CrossOrigin
@RestController
@RequestMapping(path = "/tmRating")
public class TmRatingController {

	TmRatingIService tmRatingService;

	@Autowired
	public TmRatingController(TmRatingIService tmRatingService) {
		super();
		this.tmRatingService = tmRatingService;
	}
	
	
	// -------------------Create a TmRating-------------------------------------------

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createTmRating(@Valid @RequestBody TmRatingDto tmRatingDto) {
    	try {
    		tmRatingService.saveTmRating(tmRatingDto);
            return new ResponseEntity<TmRatingDto>(tmRatingDto, HttpStatus.CREATED);
    	} catch(Exception e)
    	{
    		return new ResponseEntity<>(new CustomErrorType("Unable to create new TmRating. " + e.toString()),
    				HttpStatus.BAD_REQUEST);
    	}
    }
    
//-------------------Retrieve All TmRating--------------------------------------------------------
    
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public ResponseEntity<?> listAllTmRatings() {
    	List<TmRatingDto> tmRatingDtos = tmRatingService.findAllTmRatings();
    	if (tmRatingDtos.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
        return new ResponseEntity<List<TmRatingDto>>(tmRatingDtos, HttpStatus.OK);
    }
    
// -------------------Retrieve Single TmRating------------------------------------------
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTmRating(@PathVariable("id") int id) {
    	try {
    		TmRatingDto tmRatingDto = tmRatingService.findById(id);
    		return new ResponseEntity<TmRatingDto>(tmRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("TmRating with id " + id 
                    + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
// -------------------Retrieve TmRating by TeachingMaterial------------------------------------------
    
    @RequestMapping(value = "/findByTmId/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTmRatingByTeachingMaterial(@PathVariable("id") int id) {
    	try {
    		List<TmRatingDto> tmRatingDtos = tmRatingService.findByTeachingMaterialTeachingMaterialId(id);
    		if (tmRatingDtos.isEmpty()) {
        		return new ResponseEntity<>(new CustomErrorType("List empty."),
            			HttpStatus.NO_CONTENT);
                // You many decide to return HttpStatus.NOT_FOUND
        		//NO_CONTENT doesn't print json error
        	}
            return new ResponseEntity<List<TmRatingDto>>(tmRatingDtos, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<>(new CustomErrorType("TeachingMaterial with id " + id 
                    + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
// -------------------Retrieve TmRating By StudentId and TeachingMaterialId-----------------------
    
    @RequestMapping(value = "findByStudentAndTM/{studentId}/{teachingMaterialId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTmRatingByStudentIdAndTeachingMateriaId(
    		@PathVariable("studentId") int studentId, @PathVariable("teachingMaterialId") int teachingMaterialId) {
    	try {
    		TmRatingDto tmRatingDto = tmRatingService.findByStudentIdAndTeachingMaterialId(studentId, teachingMaterialId);
    		return new ResponseEntity<TmRatingDto>(tmRatingDto, HttpStatus.OK);
    	} catch (Exception e) {
    		return new ResponseEntity<TmRatingDto>(new TmRatingDto(), HttpStatus.NO_CONTENT);
//("TmRating with studentId " + studentId 
//                    + " and teachingMaterialId " + teachingMaterialId + " not found" + e.toString()), HttpStatus.NOT_FOUND);
        }
    }
    
}

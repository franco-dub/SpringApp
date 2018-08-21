package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.dto.RoomDto;

@RestController
@RequestMapping(path = "/scheduling")
public class SchedulingController {

	SchedulingIService schedulingService;
	@Autowired
	public SchedulingController(SchedulingIService schedulingService) {
		super();
		this.schedulingService = schedulingService;
	}
	
	@RequestMapping(value = "/findFreeRooms", method = RequestMethod.POST)
    public ResponseEntity<?> findFreeRooms(@Valid @RequestBody LectureCalendarDto lectureCalendarDto) {
    	//try {
    		List<RoomDto> freeRoomDtos = schedulingService.findFreeRooms(lectureCalendarDto);
            return new ResponseEntity<List<RoomDto>>(freeRoomDtos, HttpStatus.OK);
    	//} catch(Exception e)
    	//{
    	//	return new ResponseEntity<>(new CustomErrorType("Unable to find Free Rooms. Validation error!"),
    	//			HttpStatus.BAD_REQUEST);
    	//}
    }
}

package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.LectureCalendarIService;
import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.RoomDto;

@Service
public class SchedulingService implements SchedulingIService {

	RoomIService roomService;
	LectureCalendarIService lectureCalendarService;
	
	@Override
	@Transactional
	public List<RoomDto> findFreeRooms(ModuleDto module,  Date startTime, Date endTime, Date date) {
		Iterable<RoomDto> roomDtos = roomService.findAllRooms();
        Map<Integer, RoomDto> roomMapDto = new HashMap<>();
        roomDtos.forEach(room->{
            roomMapDto.put(room.getRoomId(), room);
        });
        Iterable<LectureCalendarDto> lectureCalendarDtos = lectureCalendarService.findAllLectureSDate(date);
        if(null!=lectureCalendarDtos){
            lectureCalendarDtos.forEach(lecture -> {
                
            	if(!((lecture.getEndTime().before(startTime) || lecture.getEndTime().equals(startTime)) &&
            		(lecture.getStartTime().after(endTime) || lecture.getStartTime().equals(endTime)))) {
            		roomMapDto.remove(lecture.getRoom().getRoomId());
            	}
            });
        }
		return new ArrayList<>(roomMapDto.values());
	}

}

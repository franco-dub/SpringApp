package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.LectureCalendarIService;
import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.dto.RoomDto;

@Service
public class SchedulingService implements SchedulingIService {

	RoomIService roomService;
	LectureCalendarIService lectureCalendarService;
	@Autowired
	public SchedulingService(RoomIService roomService, LectureCalendarIService lectureCalendarService) {
		super();
		this.roomService = roomService;
		this.lectureCalendarService = lectureCalendarService;
	}


	@Override
	@Transactional
	public List<RoomDto> findFreeRooms(LectureCalendarDto lectureCalendarDto) {
		Iterable<RoomDto> roomDtos = roomService.findAllRooms();
        Map<Integer, RoomDto> roomMapDto = new HashMap<>();
        roomDtos.forEach(room->{
            roomMapDto.put(room.getRoomId(), room);
        });
        Iterable<LectureCalendarDto> lectureCalendarDtos = lectureCalendarService.findAllLectureSDate(lectureCalendarDto.getDate());
        if(null!=lectureCalendarDtos){
            lectureCalendarDtos.forEach(lecture -> {
            	if(!(lecture.getEndTimeToLocalTime().isBefore(lectureCalendarDto.getStartTimeToLocalTime()) || 
            			lecture.getEndTimeToLocalTime().equals(lectureCalendarDto.getStartTimeToLocalTime()) ||
            			lecture.getStartTimeToLocalTime().isAfter(lectureCalendarDto.getEndTimeToLocalTime()) || 
            			lecture.getStartTimeToLocalTime().equals(lectureCalendarDto.getEndTimeToLocalTime()))) {
            		roomMapDto.remove(lecture.getRoom().getRoomId());
            	}
            });
        }
		return new ArrayList<>(roomMapDto.values());
	}

}

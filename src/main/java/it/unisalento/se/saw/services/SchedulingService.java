package it.unisalento.se.saw.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.LectureCalendarIService;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.dto.StudentDto;

@Service
public class SchedulingService implements SchedulingIService {

	RoomIService roomService;
	LectureCalendarIService lectureCalendarService;
	StudentIService studentService;
	ModuleIService moduleService;
	
	int count = 0;
	@Autowired
	public SchedulingService(RoomIService roomService, LectureCalendarIService lectureCalendarService,
			StudentIService studentService, ModuleIService moduleService) {
		super();
		this.roomService = roomService;
		this.lectureCalendarService = lectureCalendarService;
		this.studentService = studentService;
		this.moduleService = moduleService;
	}

	@Override
	@Transactional
	public List<RoomDto> findFreeRooms(LectureCalendarDto lectureCalendarDto) {
		/*Map all rooms*/
		Iterable<RoomDto> roomDtos = roomService.findAllRooms();
        Map<Integer, RoomDto> roomMapDto = new HashMap<>();
        roomDtos.forEach(room->{
            roomMapDto.put(room.getRoomId(), room);
        });
        /*Get the count of all the students attending the module*/
        List<StudentDto> studentDtos = studentService.findAllCourseSStudent(lectureCalendarDto.getModule().getCourse().getCourseId());
        if(!studentDtos.isEmpty()) {
        	for (StudentDto student : studentDtos) {
        		if(student.getYear() == lectureCalendarDto.getModule().getYear()) {
        			count+=1;
        		}
        	}
        }
        Iterable<LectureCalendarDto> lectureCalendarDtos = lectureCalendarService.findAllLectureSDate(lectureCalendarDto.getDate());
        if(null!=lectureCalendarDtos){
            lectureCalendarDtos.forEach(lecture -> {
            	/*Check if slot time's lecture of the day overlap with requested one*/
            	if(!(lecture.getEndTimeToLocalTime().isBefore(lectureCalendarDto.getStartTimeToLocalTime()) || 
            			lecture.getEndTimeToLocalTime().equals(lectureCalendarDto.getStartTimeToLocalTime()) ||
            			lecture.getStartTimeToLocalTime().isAfter(lectureCalendarDto.getEndTimeToLocalTime()) || 
            			lecture.getStartTimeToLocalTime().equals(lectureCalendarDto.getEndTimeToLocalTime()))) {
            		/*Check if room is big enough*/
            		if(count > lecture.getRoom().getCapacity())
            			roomMapDto.remove(lecture.getRoom().getRoomId());
            	}
            });
        }
		return new ArrayList<>(roomMapDto.values());
	}

}

package it.unisalento.se.saw.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.IService.ModuleIService;
import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.IService.SchedulingIService;
import it.unisalento.se.saw.IService.StudentIService;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.dto.StudentDto;
import it.unisalento.se.saw.util.DateTimeConverter;

@Service
public class SchedulingService implements SchedulingIService {
	
	RoomIService roomService;
	CalendarIService lectureCalendarService;
	StudentIService studentService;
	ModuleIService moduleService;
	
	@Autowired
	public SchedulingService(RoomIService roomService, CalendarIService lectureCalendarService,
			StudentIService studentService, ModuleIService moduleService) {
		super();
		this.roomService = roomService;
		this.lectureCalendarService = lectureCalendarService;
		this.studentService = studentService;
		this.moduleService = moduleService;
	}

	@Override
	@Transactional
	public List<RoomDto> findFreeRooms(CalendarDto lectureCalendarDto) {
		/*Map all rooms*/
		Iterable<RoomDto> roomDtos = roomService.findAllRooms();
        Map<Integer, RoomDto> roomMapDto = new HashMap<>();
        roomDtos.forEach(room->{
            roomMapDto.put(room.getRoomId(), room);
        });
        /*Get the count of all the students attending the module*/
        List<StudentDto> studentDtos = studentService.findAllCourseSStudent(lectureCalendarDto.getModule().getCourse().getCourseId());
        long cap = studentDtos.stream()
                .filter(c -> lectureCalendarDto.getModule().getYear() == c.getYear())
                .count();
        
        Iterable<CalendarDto> lectureCalendarDtos = lectureCalendarService.findAllLectureSDate(lectureCalendarDto.getDate());
        if(null!=lectureCalendarDtos){
            lectureCalendarDtos.forEach(lecture -> {
            	/*Check if slot time's lecture of the day overlap with requested one*/
            	if(!(lecture.getEndTimeToLocalTime().isBefore(lectureCalendarDto.getStartTimeToLocalTime()) || 
            			lecture.getEndTimeToLocalTime().equals(lectureCalendarDto.getStartTimeToLocalTime()) ||
            			lecture.getStartTimeToLocalTime().isAfter(lectureCalendarDto.getEndTimeToLocalTime()) || 
            			lecture.getStartTimeToLocalTime().equals(lectureCalendarDto.getEndTimeToLocalTime()))) {
            		/*Check if room is big enough*/
            		if(cap > lecture.getRoom().getCapacity())
            			roomMapDto.remove(lecture.getRoom().getRoomId());
            	}
            });
        }
		return new ArrayList<>(roomMapDto.values());
	}

	@Override
	@Transactional
	public void saveAllLectures(CalendarDto lectureCalendarDto) {
		LocalDate currentDate = lectureCalendarDto.getStartDateToLocalDate();
		do {
			lectureCalendarDto.setDate(DateTimeConverter.asDate(currentDate));
			lectureCalendarService.saveLecture(lectureCalendarDto);
			currentDate = currentDate.plusWeeks(1);
		} while(currentDate.isBefore(lectureCalendarDto.getEndDateToLocalDate()) ||
				currentDate.isEqual(lectureCalendarDto.getEndDateToLocalDate()));
	}

}

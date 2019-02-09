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
	CalendarIService calendarService;
	StudentIService studentService;
	ModuleIService moduleService;
	
	@Autowired
	public SchedulingService(RoomIService roomService, CalendarIService calendarService,
			StudentIService studentService, ModuleIService moduleService) {
		super();
		this.roomService = roomService;
		this.calendarService = calendarService;
		this.studentService = studentService;
		this.moduleService = moduleService;
	}

	@Override
	@Transactional
	public List<RoomDto> findFreeRooms(CalendarDto calendarDto) {
		/*Map all rooms*/
		Iterable<RoomDto> roomDtos = roomService.findAllRooms();
        Map<Integer, RoomDto> roomMapDto = new HashMap<>();
        roomDtos.forEach(room-> roomMapDto.put(room.getRoomId(), room));
        /*Get the count of all the students attending the module*/
        List<StudentDto> studentDtos = studentService.findAllCourseSStudent(calendarDto.getModule().getCourse().getCourseId());
        long cap = studentDtos.stream()
                .filter(c -> calendarDto.getModule().getYear() == c.getYear())
                .count();
      
        
        Iterable<CalendarDto> calendarDtos = calendarService.findAllCalendarSDate(calendarDto.getDate());
        if(null!=calendarDtos){
        	calendarDtos.forEach(calendar -> {
        		/*Check if slot time's lecture of the day overlap with requested one*/
        		if(!(calendar.getEndTimeToLocalTime().isBefore(calendarDto.getStartTimeToLocalTime()) ||
        				calendar.getEndTimeToLocalTime().equals(calendarDto.getStartTimeToLocalTime()) ||
        				calendar.getStartTimeToLocalTime().isAfter(calendarDto.getEndTimeToLocalTime()) || 
        				calendar.getStartTimeToLocalTime().equals(calendarDto.getEndTimeToLocalTime()))) {
			        assert calendar.getRoom() != null;
			        roomMapDto.remove(calendar.getRoom().getRoomId());
        		}
        	});
        }
        	
        LocalDate currentDate = calendarDto.getStartDateToLocalDate();
        do {
        	calendarDto.setDate(DateTimeConverter.asDate(currentDate));
        	//implementation
        	
        	
        	currentDate = currentDate.plusWeeks(1);
        } while (currentDate.isBefore(calendarDto.getEndDateToLocalDate()) ||
				currentDate.isEqual(calendarDto.getEndDateToLocalDate()));
		return new ArrayList<>(roomMapDto.values());
	}

	@Override
	@Transactional
	public void saveAllCalendars(CalendarDto calendarDto) {
		LocalDate currentDate = calendarDto.getStartDateToLocalDate();
		do {
			calendarDto.setDate(DateTimeConverter.asDate(currentDate));
			calendarService.saveCalendar(calendarDto);
			currentDate = currentDate.plusWeeks(1);
		} while(currentDate.isBefore(calendarDto.getEndDateToLocalDate()) ||
				currentDate.isEqual(calendarDto.getEndDateToLocalDate()));
	}

}

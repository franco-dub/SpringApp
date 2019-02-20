package it.unisalento.se.saw.services;

import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.builder.CalendarExamType;
import it.unisalento.se.saw.builder.CalendarLessonType;
import it.unisalento.se.saw.domain.Calendar;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.repo.CalendarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService implements CalendarIService {

	private CalendarLessonType calendarLessonType = new CalendarLessonType();
	private CalendarExamType calendarExamType = new CalendarExamType();
	private static final ModelMapper modelMapper = new ModelMapper();

	CalendarRepository lectureCalendarRepository;

	@Autowired
	public CalendarService(CalendarRepository lectureCalendarRepository) {
		super();
		this.lectureCalendarRepository = lectureCalendarRepository;
	}

	@Override
	@Transactional
	public CalendarDto findById(Integer id) {
		Calendar calendar = lectureCalendarRepository.findById(id).get();
		CalendarDto calendarDto = mapCalendarDto(calendar);
		System.out.println(calendarDto.getCalendarDate().type().type());
		calendarDto.setDay(calendar.getDay());
		return calendarDto;
	}

	@Override
	@Transactional
	public CalendarDto saveCalendar(CalendarDto calendarDto) {
		Calendar calendar = modelMapper.map(calendarDto, Calendar.class);
		calendar.setEndTime(calendarDto.getCalendarDate().getEndTime());
		calendar.setStartTime(calendarDto.getCalendarDate().getStartTime());
		calendar.setType(calendarDto.getCalendarDate().getType());
		calendar.setDay(calendarDto.getDay());
		return modelMapper.map(lectureCalendarRepository.save(calendar), CalendarDto.class);
	}

	@Override
	@Transactional
	public CalendarDto updateCalendar(CalendarDto calendarDto) {
		return saveCalendar(calendarDto);
	}

	@Override
	@Transactional
	public void deleteCalendarById(Integer id) {
		lectureCalendarRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<CalendarDto> findAllCalendars() {
		List<Calendar> calendars = lectureCalendarRepository.findAll();
		List<CalendarDto> calendarDtos = new ArrayList<>();
		calendars.forEach(calendar -> calendarDtos.add(mapCalendarDto(calendar)));
		return calendarDtos;
	}

	@Override
	@Transactional
	public List<CalendarDto> findAllCalendarSDate(Date date) {
		List<Calendar> calendars = lectureCalendarRepository.findAllByDate(date);
		List<CalendarDto> calendarDtos = new ArrayList<>();
		calendars.forEach(calendar -> calendarDtos.add(mapCalendarDto(calendar)));
		return calendarDtos;
	}
	
	@Override
	@Transactional
	public List<CalendarDto> findAllCalendarByModule(Integer moduleId) {
		List<Calendar> calendars = lectureCalendarRepository.findAllByModuleModuleId(moduleId);
		List<CalendarDto> calendarDtos = new ArrayList<>();
		calendars.forEach(calendar -> calendarDtos.add(mapCalendarDto(calendar)));
		return calendarDtos;
	}

	@Override
	public List<CalendarDto> findAllCalendarByModuleAndDate(Integer moduleId, Date date) {
		List<Calendar> calendars = lectureCalendarRepository.findCalendarByModuleAndDate(date, moduleId);
		List<CalendarDto> calendarDtos = new ArrayList<>();
		calendars.forEach(calendar -> calendarDtos.add(mapCalendarDto(calendar)));
		return calendarDtos;
	}

	private CalendarDto mapCalendarDto(Calendar calendar){
		CalendarDto calendarDto = new CalendarDto();
		calendarDto.setModule(modelMapper.map(calendar.getModule(), ModuleDto.class));
		calendarDto.setRoom(modelMapper.map(calendar.getRoom(), RoomDto.class));
		calendarDto.setCalendarId(calendar.getCalendarId());
		if(calendar.getType().equals("LECTURE")){
			calendarDto.setDay(calendar.getDay());
			calendarDto.setCalendarDate(calendarLessonType.calendarDate(
					calendar.getStartTime(),
					calendar.getEndTime(),
					calendar.getDate(),
					null, null, null));
		}else{
			calendarDto.setCalendarDate(calendarExamType.calendarDate(
					calendar.getStartTime(),
					calendar.getEndTime(),
					calendar.getDate(),
					null, null, null));
		}
		return calendarDto;
	}
	
}

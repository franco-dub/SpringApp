package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.CalendarIService;
import it.unisalento.se.saw.domain.Calendar;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.repo.CalendarRepository;

@Service
public class CalendarService implements CalendarIService {

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
		return modelMapper.map(calendar, CalendarDto.class);
	}

	@Override
	@Transactional
	public void saveCalendar(CalendarDto calendarDto) {
		Calendar calendar = modelMapper.map(calendarDto, Calendar.class);
		lectureCalendarRepository.save(calendar);
	}

	@Override
	@Transactional
	public void updateCalendar(CalendarDto calendarDto) {
		saveCalendar(calendarDto);
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
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> calendarDtos = modelMapper.map(calendars, targetListType);
		return calendarDtos;
	}

	@Override
	@Transactional
	public List<CalendarDto> findAllCalendarSDate(Date date) {
		List<Calendar> calendars = lectureCalendarRepository.findAllByDate(date);
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> calendarDtos = modelMapper.map(calendars, targetListType);
		return calendarDtos;
	}
	
	@Override
	@Transactional
	public List<CalendarDto> findAllCalendarByModule(Integer moduleId) {
		List<Calendar> calendars = lectureCalendarRepository.findAllByModuleModuleId(moduleId);
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> calendarDtos = modelMapper.map(calendars, targetListType);
		return calendarDtos;
	}

	@Override
	public List<CalendarDto> findAllCalendarByModuleAndDate(Integer moduleId, Date date) {
		List<Calendar> calendars = lectureCalendarRepository.findCalendarByModuleAndDate(date, moduleId);
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> calendarDtos = modelMapper.map(calendars, targetListType);
		return calendarDtos;
	}
	
}

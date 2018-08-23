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
	public void saveLecture(CalendarDto lectureCalendarDto) {
		Calendar lectureCalendar = modelMapper.map(lectureCalendarDto, Calendar.class);
		lectureCalendarRepository.save(lectureCalendar);
	}

	@Override
	@Transactional
	public void updateLecture(CalendarDto lectureCalendarDto) {
		saveLecture(lectureCalendarDto);
	}

	@Override
	@Transactional
	public void deleteLectureById(Integer id) {
		lectureCalendarRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<CalendarDto> findAllLectures() {
		List<Calendar> lectures = lectureCalendarRepository.findAll();
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> lectureDtos = modelMapper.map(lectures, targetListType);
		return lectureDtos;
	}

	@Override
	@Transactional
	public List<CalendarDto> findAllLectureSDate(Date date) {
		List<Calendar> lectures = lectureCalendarRepository.findAllByDate(date);
		Type targetListType = new TypeToken<List<CalendarDto>>() {}.getType();
		List<CalendarDto> lectureDtos = modelMapper.map(lectures, targetListType);
		return lectureDtos;
	}
}

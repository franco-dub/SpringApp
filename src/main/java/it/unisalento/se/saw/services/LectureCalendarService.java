package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.LectureCalendarIService;
import it.unisalento.se.saw.domain.LectureCalendar;
import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.repo.LectureCalendarRepository;

@Service
public class LectureCalendarService implements LectureCalendarIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	LectureCalendarRepository lectureCalendarRepository;
	@Autowired
	public LectureCalendarService(LectureCalendarRepository lectureCalendarRepository) {
		super();
		this.lectureCalendarRepository = lectureCalendarRepository;
	}

	@Override
	@Transactional
	public LectureCalendarDto findById(Integer id) {
		LectureCalendar lectureCalendar = lectureCalendarRepository.findById(id).get();
		return modelMapper.map(lectureCalendar, LectureCalendarDto.class);
	}

	@Override
	@Transactional
	public void saveLecture(LectureCalendarDto lectureCalendarDto) {
		LectureCalendar lectureCalendar = modelMapper.map(lectureCalendarDto, LectureCalendar.class);
		lectureCalendarRepository.save(lectureCalendar);
	}

	@Override
	@Transactional
	public void updateLecture(LectureCalendarDto lectureCalendarDto) {
		saveLecture(lectureCalendarDto);
	}

	@Override
	@Transactional
	public void deleteLectureById(Integer id) {
		lectureCalendarRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<LectureCalendarDto> findAllLectures() {
		List<LectureCalendar> lectures = lectureCalendarRepository.findAll();
		Type targetListType = new TypeToken<List<LectureCalendarDto>>() {}.getType();
		List<LectureCalendarDto> lectureDtos = modelMapper.map(lectures, targetListType);
		return lectureDtos;
	}

}

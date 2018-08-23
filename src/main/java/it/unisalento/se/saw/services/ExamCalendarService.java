package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.ExamCalendarIService;
import it.unisalento.se.saw.domain.ExamCalendar;
import it.unisalento.se.saw.dto.ExamCalendarDto;
import it.unisalento.se.saw.repo.ExamCalendarRepository;

@Service
public class ExamCalendarService implements ExamCalendarIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	ExamCalendarRepository examCalendarRepository;
	@Autowired
	public ExamCalendarService(ExamCalendarRepository examCalendarRepository) {
		super();
		this.examCalendarRepository = examCalendarRepository;
	}

	@Override
	@Transactional
	public ExamCalendarDto findById(Integer id) {
		ExamCalendar examCalendar = examCalendarRepository.findById(id).get();
		return modelMapper.map(examCalendar, ExamCalendarDto.class);
	}

	@Override
	@Transactional
	public void saveExam(ExamCalendarDto examCalendarDto) {
		ExamCalendar examCalendar = modelMapper.map(examCalendarDto, ExamCalendar.class);
		examCalendarRepository.save(examCalendar);
	}

	@Override
	@Transactional
	public void updateExam(ExamCalendarDto examCalendarDto) {
		saveExam(examCalendarDto);
	}

	@Override
	@Transactional
	public void deleteExamById(Integer id) {
		examCalendarRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<ExamCalendarDto> findAllExams() {
		List<ExamCalendar> exams = examCalendarRepository.findAll();
		Type targetListType = new TypeToken<List<ExamCalendarDto>>() {}.getType();
		List<ExamCalendarDto> examDtos = modelMapper.map(exams, targetListType);
		return examDtos;
	}

}

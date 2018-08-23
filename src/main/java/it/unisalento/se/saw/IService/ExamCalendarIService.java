package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.ExamCalendarDto;

public interface ExamCalendarIService {

	public ExamCalendarDto findById(Integer id);
	public void saveExam(ExamCalendarDto examCalendarDto);
	public void updateExam(ExamCalendarDto examCalendarDto);
	public void deleteExamById(Integer id);
	public List<ExamCalendarDto> findAllExams();
}

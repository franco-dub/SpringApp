package it.unisalento.se.saw.IService;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.dto.CalendarDto;

public interface CalendarIService {
	public CalendarDto findById(Integer id);
	public void saveLecture(CalendarDto lectureCalendarDto);
	public void updateLecture(CalendarDto lectureCalendarDto);
	public void deleteLectureById(Integer id);
	public List<CalendarDto> findAllLectures();
	public List<CalendarDto> findAllLectureSDate(Date date);
}

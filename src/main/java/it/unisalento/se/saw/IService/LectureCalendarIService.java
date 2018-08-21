package it.unisalento.se.saw.IService;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.dto.LectureCalendarDto;

public interface LectureCalendarIService {
	public LectureCalendarDto findById(Integer id);
	public void saveLecture(LectureCalendarDto lectureCalendarDto);
	public void updateLecture(LectureCalendarDto lectureCalendarDto);
	public void deleteLectureById(Integer id);
	public List<LectureCalendarDto> findAllLectures();
	public List<LectureCalendarDto> findAllLectureSDate(Date date);
}

package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.LectureRatingDto;

public interface LectureRatingIService {

	public LectureRatingDto findById(Integer id);
	public void saveLectureRating(LectureRatingDto lectureRatingDto);
	public List<LectureRatingDto> findAll();
	public List<LectureRatingDto> findAllBylecture(Integer id);
	public LectureRatingDto findByStudentIdAndLectureId(Integer studentId, Integer calendarId);
}

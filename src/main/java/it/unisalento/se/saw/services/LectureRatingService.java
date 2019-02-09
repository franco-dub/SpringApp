package it.unisalento.se.saw.services;

import java.util.List;
import java.lang.reflect.Type;
import org.modelmapper.TypeToken;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.se.saw.IService.LectureRatingIService;
import it.unisalento.se.saw.domain.LectureRating;
import it.unisalento.se.saw.dto.LectureRatingDto;
import it.unisalento.se.saw.repo.LectureRatingRepository;

@Service
public class LectureRatingService implements LectureRatingIService {
	private static final ModelMapper modelMapper = new ModelMapper();
	LectureRatingRepository lectureRatingRepository;
	
	@Autowired
	public LectureRatingService(LectureRatingRepository lectureRatingRepository) {
		super();
		this.lectureRatingRepository = lectureRatingRepository;
	}

	@Override
	public LectureRatingDto findById(Integer id) {
		LectureRating lectureRating = lectureRatingRepository.findById(id).get();
		return modelMapper.map(lectureRating, LectureRatingDto.class);
	}

	@Override
	public void saveLectureRating(LectureRatingDto lectureRatingDto) {
		LectureRating lectureRating = modelMapper.map(lectureRatingDto, LectureRating.class);
		lectureRatingRepository.save(lectureRating);
	}

	@Override
	public List<LectureRatingDto> findAll() {
		List<LectureRating> lectureRatings = lectureRatingRepository.findAll();
		Type targetListType = new TypeToken<List<LectureRatingDto>>() {}.getType();
		List<LectureRatingDto> lectureRatingDtos = modelMapper.map(lectureRatings, targetListType);
		return lectureRatingDtos;
	}

	@Override
	public List<LectureRatingDto> findAllBylecture(Integer calendarId){
		List<LectureRating> lectureRatings = lectureRatingRepository.findByCalendarCalendarId(calendarId);
		Type targetListType = new TypeToken<List<LectureRatingDto>>() {}.getType();
		List<LectureRatingDto> lectureRatingDtos = modelMapper.map(lectureRatings, targetListType);
		return lectureRatingDtos;
	}

	@Override
	public LectureRatingDto findByStudentIdAndLectureId(Integer studentId, Integer calendarId) {
		LectureRating lectureRating = lectureRatingRepository.findByStudentAndLecture(studentId, calendarId);
		return modelMapper.map(lectureRating, LectureRatingDto.class);
	}

}

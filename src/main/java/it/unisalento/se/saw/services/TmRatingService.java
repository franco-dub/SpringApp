package it.unisalento.se.saw.services;

import java.util.List;
import java.util.Optional;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.TmRatingIService;
import it.unisalento.se.saw.domain.TmRating;
import it.unisalento.se.saw.dto.TmRatingDto;
import it.unisalento.se.saw.repo.TmRatingRepository;

@Service
public class TmRatingService implements TmRatingIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	TmRatingRepository tmRatingRepository;
	@Autowired
	public TmRatingService(TmRatingRepository tmRatingRepository) {
		super();
		this.tmRatingRepository = tmRatingRepository;
	}
	
	@Override
	public TmRatingDto findById(Integer id) {
		TmRating tmRating = tmRatingRepository.findById(id).get();
		return modelMapper.map(tmRating, TmRatingDto.class);
	}

	@Override
	@Transactional
	public void saveTmRating(TmRatingDto tmRatingDto) {
		TmRating tmRating = modelMapper.map(tmRatingDto, TmRating.class);
		tmRatingRepository.save(tmRating);
	}

	@Override
	public List<TmRatingDto> findAllTmRatings() {
		List<TmRating> tmRatings = tmRatingRepository.findAll();
		Type targetListType = new TypeToken<List<TmRatingDto>>() {}.getType();
		List<TmRatingDto> tmRatingDtos = modelMapper.map(tmRatings, targetListType);
		return tmRatingDtos;
	}

	@Override
	public List<TmRatingDto> findByTeachingMaterialTeachingMaterialId(Integer id) {
		List<TmRating> tmRatings = tmRatingRepository.findByTeachingMaterialTeachingMaterialId(id);
		Type targetListType = new TypeToken<List<TmRatingDto>>() {}.getType();
		List<TmRatingDto> tmRatingDtos = modelMapper.map(tmRatings, targetListType);
		return tmRatingDtos;
	}

	@Override
	public TmRatingDto findByStudentIdAndTeachingMaterialId(Integer studentId, Integer teachingMaterialId) {
		TmRating tmRating = tmRatingRepository.findByStudentAndTm(teachingMaterialId, studentId);
		return modelMapper.map(tmRating, TmRatingDto.class);
	}

}

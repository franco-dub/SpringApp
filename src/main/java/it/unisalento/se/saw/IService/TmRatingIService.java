package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.TmRatingDto;

public interface TmRatingIService {

	public TmRatingDto findById(Integer id);
	public void saveTmRating(TmRatingDto tmRatingDto);
	public List<TmRatingDto> findAllTmRatings();
	public List<TmRatingDto> findByTeachingMaterialTeachingMaterialId(Integer id);
}

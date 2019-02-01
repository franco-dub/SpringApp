package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;


public class TmRatingDto {

	private Integer tmRatingId;
	@NotNull
    private StudentDto student;
	@NotNull
    private TeachingMaterialDto teachingMaterial;
	@NotNull
    private String rate;
	
	public TmRatingDto() {}

	public Integer getTmRatingId() {
		return tmRatingId;
	}

	public void setTmRatingId(Integer tmRatingId) {
		this.tmRatingId = tmRatingId;
	}

	public StudentDto getStudent() {
		return student;
	}

	public void setStudent(StudentDto student) {
		this.student = student;
	}

	public TeachingMaterialDto getTeachingMaterial() {
		return teachingMaterial;
	}

	public void setTeachingMaterial(TeachingMaterialDto teachingMaterial) {
		this.teachingMaterial = teachingMaterial;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
}

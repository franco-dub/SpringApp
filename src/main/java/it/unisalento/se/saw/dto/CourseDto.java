package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class CourseDto {
	private Integer courseId;
	@NotNull
    private String name;
    private String description;
    private Integer year;
    private Integer cfu;
    /*ENUM('BACHELOR', 'MASTER', 'ALL_IN_ONE_CYCLE_MASTER')*/
    private String courseType;
    
    public CourseDto() {}
	    

	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getCfu() {
		return cfu;
	}
	public void setCfu(Integer cfu) {
		this.cfu = cfu;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public String getCourseType() {
		return courseType;
	}


	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	

}

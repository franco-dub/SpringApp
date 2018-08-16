package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class CourseDto {
	
	@NotNull
    private String name;
    private String description;
    
	public CourseDto(String name) {
		super();
		this.name = name;
	}
	public CourseDto(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	protected CourseDto() {}
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
	

}

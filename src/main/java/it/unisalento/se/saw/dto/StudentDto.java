package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class StudentDto {

	@NotNull
    private CourseDto courseDto;
	@NotNull
    private PersonDto personDto;
	@NotNull
    private Date registrationDate;
    private Date graduationDate;
	public StudentDto(CourseDto courseDto, PersonDto personDto, Date registrationDate) {
		super();
		this.courseDto = courseDto;
		this.personDto = personDto;
		this.registrationDate = registrationDate;
	}
	public StudentDto(CourseDto courseDto, PersonDto personDto, Date registrationDate, Date graduationDate) {
		super();
		this.courseDto = courseDto;
		this.personDto = personDto;
		this.registrationDate = registrationDate;
		this.graduationDate = graduationDate;
	}
	protected StudentDto() {}
	public CourseDto getCourseDto() {
		return courseDto;
	}
	public void setCourseDto(CourseDto courseDto) {
		this.courseDto = courseDto;
	}
	public PersonDto getPersonDto() {
		return personDto;
	}
	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Date getGraduationDate() {
		return graduationDate;
	}
	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}
}

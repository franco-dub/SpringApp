package it.unisalento.se.saw.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.unisalento.se.saw.util.DateTimeConverter;

public class StudentDto {

	private static final DateTimeConverter converter = new DateTimeConverter();
	private Integer studentId;
	@NotNull
    private CourseDto course;
	@NotNull
    private PersonDto person;
	@NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date registrationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date graduationDate;

	public StudentDto() {}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
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

	public long getYear() {
		LocalDate now = converter.convertToLocalDate(new Date());
		long years = java.time.temporal.ChronoUnit.YEARS.between(converter.convertToLocalDate(this.getRegistrationDate()), now);
		return years+1;
	}
}

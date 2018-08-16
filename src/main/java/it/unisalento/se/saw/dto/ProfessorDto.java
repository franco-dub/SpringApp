package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ProfessorDto {
	
	private Integer professorId;
	@NotNull
    private PersonDto person;
	@NotNull
    private String level;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date hireDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date endEngagement;
    
	public ProfessorDto() {}

	public Integer getProfessorId() {
		return professorId;
	}
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}
	public PersonDto getPerson() {
		return person;
	}
	public void setPerson(PersonDto person) {
		this.person = person;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Date getHireDate() {
		return hireDate;
	}
	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}
	public Date getEndEngagement() {
		return endEngagement;
	}
	public void setEndEngagement(Date endEngagement) {
		this.endEngagement = endEngagement;
	}
}

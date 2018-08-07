package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;


public class ProfessorDto {
	
	@NotNull
    private PersonDto personDto;
	@NotNull
    private String level;
	@NotNull
    private Date hireDate;
    private Date endEngagement;
    
	public ProfessorDto(PersonDto personDto, String level, Date hireDate) {
		super();
		this.personDto = personDto;
		this.level = level;
		this.hireDate = hireDate;
	}
	public ProfessorDto(PersonDto personDto, String level, Date hireDate, Date endEngagement) {
		super();
		this.personDto = personDto;
		this.level = level;
		this.hireDate = hireDate;
		this.endEngagement = endEngagement;
	}
	protected ProfessorDto() {}
	public PersonDto getPersonDto() {
		return personDto;
	}
	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
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

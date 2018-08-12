package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class SecretaryDto {

	@NotNull
    private PersonDto personDto;
	@NotNull
    private Date hireDate;
    private Date endEngagement;
    private String task;
    
	protected SecretaryDto() {}
	public SecretaryDto(PersonDto personDto, Date hireDate) {
		super();
		this.personDto = personDto;
		this.hireDate = hireDate;
	}
	public SecretaryDto(PersonDto personDto, Date hireDate, Date endEngagement, String task) {
		super();
		this.personDto = personDto;
		this.hireDate = hireDate;
		this.endEngagement = endEngagement;
		this.task = task;
	}
	public PersonDto getPersonDto() {
		return personDto;
	}
	public void setPersonDto(PersonDto personDto) {
		this.personDto = personDto;
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
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
    
    
}

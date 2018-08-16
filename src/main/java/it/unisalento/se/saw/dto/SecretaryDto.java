package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class SecretaryDto {

	private Integer secretaryId;
	@NotNull
    private PersonDto person;
	@NotNull
    private Date hireDate;
    private Date endEngagement;
    private String task;
    
	public SecretaryDto() {}
	
	public Integer getSecretaryId() {
		return secretaryId;
	}

	public void setSecretaryId(Integer secretaryId) {
		this.secretaryId = secretaryId;
	}


	public void setPerson(PersonDto person) {
		this.person = person;
	}


	public PersonDto getPerson() {
		return person;
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

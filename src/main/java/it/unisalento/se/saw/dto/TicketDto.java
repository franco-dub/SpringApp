package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;


public class TicketDto {

	private Integer ticketId;
	@NotNull
	private String title;
	@NotNull
    private ProfessorDto professor;
	@NotNull
    private RoomDto room;
    private SecretaryDto secretary;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Europe/Rome")
    private Date date;
    @NotNull
    private String description;
    private String status;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Europe/Rome")
    private Date lastModified;
    
    
	public Integer getTicketId() {
		return ticketId;
	}
	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ProfessorDto getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorDto professor) {
		this.professor = professor;
	}
	public RoomDto getRoom() {
		return room;
	}
	public void setRoom(RoomDto room) {
		this.room = room;
	}
	public SecretaryDto getSecretary() {
		return secretary;
	}
	public void setSecretary(SecretaryDto secretary) {
		this.secretary = secretary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

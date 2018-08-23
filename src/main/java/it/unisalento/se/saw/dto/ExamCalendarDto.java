package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExamCalendarDto {

	private Integer examCalendarId;
	@NotNull
    private ModuleDto module;
	@NotNull
    private RoomDto room;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date date;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
    private Date startTime;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
    private Date endTime;
	@NotNull
    private String day;
	public Integer getExamCalendarId() {
		return examCalendarId;
	}
	public void setExamCalendarId(Integer examCalendarId) {
		this.examCalendarId = examCalendarId;
	}
	public ModuleDto getModule() {
		return module;
	}
	public void setModule(ModuleDto module) {
		this.module = module;
	}
	public RoomDto getRoom() {
		return room;
	}
	public void setRoom(RoomDto room) {
		this.room = room;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	
}

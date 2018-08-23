package it.unisalento.se.saw.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import it.unisalento.se.saw.util.DateTimeConverter;

public class CalendarDto {
	
	private static final DateTimeConverter converter = new DateTimeConverter();
	
	private Integer lectureCalendarId;
	@NotNull
    private ModuleDto module;
	@Nullable
    private RoomDto room;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
    private Date startTime;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
    private Date endTime;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
    private Date date;
	@NotNull
    private String day;
	
	private Date startDate;
	private Date endDate;
	
	public CalendarDto() {}

	public Integer getLectureCalendarId() {
		return lectureCalendarId;
	}

	public void setLectureCalendarId(Integer lectureCalendarId) {
		this.lectureCalendarId = lectureCalendarId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	@JsonIgnore
	public LocalTime getStartTimeToLocalTime() {
		return converter.convertToLocalTime(this.getStartTime());
	}
	@JsonIgnore
	public LocalTime getEndTimeToLocalTime() {
		return converter.convertToLocalTime(this.getEndTime());
	}
	@JsonIgnore
	public LocalDate getDateToLocalDate() {
		return converter.convertToLocalDate(this.getDate());
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonIgnore
	public LocalDate getStartDateToLocalDate() {
		return converter.convertToLocalDate(this.getStartDate());
	}
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@JsonIgnore
	public LocalDate getEndDateToLocalDate() {
		return converter.convertToLocalDate(this.getEndDate());
	}
}

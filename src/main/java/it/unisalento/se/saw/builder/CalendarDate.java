package it.unisalento.se.saw.builder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.Nullable;
import it.unisalento.se.saw.util.DateTimeConverter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class CalendarDate implements CalendarIDate{
	private static final DateTimeConverter converter = new DateTimeConverter();

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
	private Date startTime;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Europe/Rome")
	private Date endTime;
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy", timezone = "Europe/Rome")
	private Date date;

	private Date startDate;
	private Date endDate;
	private String type;

	CalendarDate(){}

	CalendarDate(Date startTime, Date endTime, Date date, CalendarType type, @Nullable Date startDate, @Nullable Date endDate){
		setStartTime(startTime);
		setEndTime(endTime);
		setDate(date);
		setStartDate(startDate);
		setEndDate(endDate);
		this.type = type.type();
	}

	@Override
	public String getType(){
		return type;
	}

	@Override
	public void setType(String type){
		this.type = type;
	}

	@Override
	public Date getStartTime() {
		return startTime;
	}

	@Override
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Override
	public Date getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@JsonIgnore
	@Override
	public LocalDate getStartDateToLocalDate() {
		return converter.convertToLocalDate(this.getStartDate());
	}

	@JsonIgnore
	@Override
	public LocalTime getStartTimeToLocalTime() {
		return converter.convertToLocalTime(this.getStartTime());
	}

	@JsonIgnore
	@Override
	public LocalTime getEndTimeToLocalTime() {
		return converter.convertToLocalTime(this.getEndTime());
	}

	@JsonIgnore
	@Override
	public LocalDate getDateToLocalDate() {
		return converter.convertToLocalDate(this.getDate());
	}

	@JsonIgnore
	@Override
	public LocalDate getEndDateToLocalDate() {
		return converter.convertToLocalDate(this.getEndDate());
	}

	@Override
	public CalendarDate calendarDate(Date startTime, Date endTime, Date date, CalendarType type,
	                                 @Nullable Date startDate, @Nullable Date endDate){
		return null;
	}

	@Override
	public CalendarType type(){
		return () -> type;
	}

}

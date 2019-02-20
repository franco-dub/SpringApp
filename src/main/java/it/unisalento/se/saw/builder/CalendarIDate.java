package it.unisalento.se.saw.builder;

import com.sun.istack.Nullable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public interface CalendarIDate{

	public CalendarDate calendarDate(Date startTime, Date endTime, Date date, CalendarType type,
	                                 @Nullable Date startDate, @Nullable Date endDate);
	public LocalTime getStartTimeToLocalTime();
	public LocalTime getEndTimeToLocalTime();
	public LocalDate getDateToLocalDate();

	void setEndDate(Date endDate);

	public LocalDate getStartDateToLocalDate();
	public LocalDate getEndDateToLocalDate();
	public CalendarType type();

	String getType();

	void setType(String type);

	Date getStartTime();

	void setStartTime(Date startTime);

	Date getEndTime();

	void setEndTime(Date endTime);

	public Date getDate();
	public void setDate(Date date);

	void setStartDate(Date startDate);

	public Date getEndDate();
	public Date getStartDate();
}

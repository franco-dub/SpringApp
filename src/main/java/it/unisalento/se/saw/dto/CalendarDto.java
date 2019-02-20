package it.unisalento.se.saw.dto;

import it.unisalento.se.saw.builder.CalendarDate;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

public class CalendarDto {

	private Integer calendarId;
	@NotNull
    private ModuleDto module;
	@Nullable
    private RoomDto room;
	@NotNull
    private String day;
	private CalendarDate calendarDate;

	public CalendarDto() {}

	public Integer getCalendarId() {
		return calendarId;
	}

	public void setCalendarId(Integer calendarId) {
		this.calendarId = calendarId;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public CalendarDate getCalendarDate(){
		return calendarDate;
	}

	public void setCalendarDate(CalendarDate calendarDate){
		this.calendarDate = calendarDate;
	}
}

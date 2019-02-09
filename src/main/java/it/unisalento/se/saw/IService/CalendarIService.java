package it.unisalento.se.saw.IService;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.dto.CalendarDto;

public interface CalendarIService {
	public CalendarDto findById(Integer id);
	public void saveCalendar(CalendarDto calendarDto);
	public void updateCalendar(CalendarDto calendarDto);
	public void deleteCalendarById(Integer id);
	public List<CalendarDto> findAllCalendars();
	public List<CalendarDto> findAllCalendarSDate(Date date);
	public List<CalendarDto> findAllCalendarByModule(Integer moduleId);
	public List<CalendarDto> findAllCalendarByModuleAndDate(Integer moduleId, Date date);
}

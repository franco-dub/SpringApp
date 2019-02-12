package it.unisalento.se.saw.IService;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.domain.Person;
import it.unisalento.se.saw.dto.CalendarDto;
import it.unisalento.se.saw.dto.ModuleDto;

public interface CalendarIService {
	public CalendarDto findById(Integer id);
	public CalendarDto saveCalendar(CalendarDto calendarDto);
	public CalendarDto updateCalendar(CalendarDto calendarDto);
	public void deleteCalendarById(Integer id);
	public List<CalendarDto> findAllCalendars();
	public List<CalendarDto> findAllCalendarSDate(Date date);
	public List<CalendarDto> findAllCalendarByModule(Integer moduleId);
	public List<CalendarDto> findAllCalendarByModuleAndDate(Integer moduleId, Date date);
}

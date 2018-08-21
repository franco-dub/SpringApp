package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.LectureCalendarDto;
import it.unisalento.se.saw.dto.RoomDto;

public interface SchedulingIService {
	public List<RoomDto> findFreeRooms(LectureCalendarDto lectureCalendarDto);
}

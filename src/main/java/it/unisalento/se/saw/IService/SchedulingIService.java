package it.unisalento.se.saw.IService;

import java.util.Date;
import java.util.List;

import it.unisalento.se.saw.dto.ModuleDto;
import it.unisalento.se.saw.dto.RoomDto;

public interface SchedulingIService {

	public List<RoomDto> findFreeRooms(ModuleDto module,  Date startTime, Date endTime, Date date);
}

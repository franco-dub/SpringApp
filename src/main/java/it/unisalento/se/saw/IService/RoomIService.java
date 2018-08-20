package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.RoomDto;

public interface RoomIService {

	public RoomDto findById(Integer id);
	public RoomDto saveRoom(RoomDto roomDto);
	public void updateRoom(RoomDto roomDto);
	public void deleteRoomById(Integer id);
	public List<RoomDto> findAllRooms();
}

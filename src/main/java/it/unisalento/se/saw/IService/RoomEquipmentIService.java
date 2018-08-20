package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.RoomEquipmentDto;

public interface RoomEquipmentIService {

	public RoomEquipmentDto findById(Integer id);
	public RoomEquipmentDto saveRoomEquipment(RoomEquipmentDto roomEquipmentDto);
	public RoomEquipmentDto updateRoomEquipment(RoomEquipmentDto roomEquipmentDto);
	public List<RoomEquipmentDto> findRoomEquipmentsByroomId(Integer id);
	public void deleteRoomEquipmentById(Integer id);
	public List<RoomEquipmentDto> findAllRoomEquipments();
	public List<RoomEquipmentDto> findAllRoomSEquipments(Integer id);
	public List<RoomEquipmentDto> findAllRoomsWhereEquipments(Integer id);
}

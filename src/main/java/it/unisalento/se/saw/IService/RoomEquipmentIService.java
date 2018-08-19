package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.RoomEquipmentDto;

public interface RoomEquipmentIService {

	public RoomEquipmentDto findById(Integer id);
	public void saveRoomEquipment(RoomEquipmentDto roomEquipmentDto);
	public void updateRoomEquipment(RoomEquipmentDto roomEquipmentDto);
	public void deleteRoomEquipmentById(Integer id);
	public List<RoomEquipmentDto> findAllRoomEquipments();
}

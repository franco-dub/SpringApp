package it.unisalento.se.saw.IService;

import java.util.List;

import it.unisalento.se.saw.dto.EquipmentDto;

public interface EquipmentIService {
	public EquipmentDto findById(Integer id);
	public void saveEquipment(EquipmentDto equipmentDto);
	public void updateEquipment(EquipmentDto equipmentDto);
	public void deleteEquipmentById(Integer id);
	public List<EquipmentDto> findAllEquipments();
}

package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.EquipmentIService;
import it.unisalento.se.saw.domain.Equipment;
import it.unisalento.se.saw.dto.EquipmentDto;
import it.unisalento.se.saw.repo.EquipmentRepository;

@Service
public class EquipmentService implements EquipmentIService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	EquipmentRepository equipmentRepository;
	@Autowired
	public EquipmentService(EquipmentRepository equipmentRepository) {
		super();
		this.equipmentRepository = equipmentRepository;
	}

	@Override
	@Transactional
	public EquipmentDto findById(Integer id) {
		Equipment equipment = equipmentRepository.findById(id).get();
		EquipmentDto equipmentDto = modelMapper.map(equipment, EquipmentDto.class);
		return equipmentDto;
	}

	@Override
	@Transactional
	public void saveEquipment(EquipmentDto equipmentDto) {
		Equipment equipment = modelMapper.map(equipmentDto, Equipment.class);
		equipmentRepository.save(equipment);
	}

	@Override
	@Transactional
	public void updateEquipment(EquipmentDto equipmentDto) {
		saveEquipment(equipmentDto);
	}

	@Override
	@Transactional
	public void deleteEquipmentById(Integer id) {
		equipmentRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<EquipmentDto> findAllEquipments() {
		List<Equipment> equipments = equipmentRepository.findAll();
		Type targetListType = new TypeToken<List<EquipmentDto>>() {}.getType();
		List<EquipmentDto> equipmentDtos = modelMapper.map(equipments, targetListType);
		return equipmentDtos;
	}

}

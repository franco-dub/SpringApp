package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.RoomEquipmentIService;
import it.unisalento.se.saw.domain.RoomEquipment;
import it.unisalento.se.saw.dto.RoomEquipmentDto;
import it.unisalento.se.saw.repo.RoomEquipmentRepository;

@Service
public class RoomEquipmentService implements RoomEquipmentIService {
	
	private static final ModelMapper modelMapper = new ModelMapper();
	RoomEquipmentRepository roomEquipmentRepository;
	@Autowired
	public RoomEquipmentService(RoomEquipmentRepository roomEquipmentRepository) {
		super();
		this.roomEquipmentRepository = roomEquipmentRepository;
	}

	@Override
	@Transactional
	public RoomEquipmentDto findById(Integer id) {
		RoomEquipment roomEquipment = roomEquipmentRepository.findById(id).get();
		RoomEquipmentDto roomEquipmentDto = modelMapper.map(roomEquipment, RoomEquipmentDto.class);
		return roomEquipmentDto;
	}

	@Override
	@Transactional
	public RoomEquipmentDto saveRoomEquipment(RoomEquipmentDto roomEquipmentDto) {
		RoomEquipment roomEquipment = modelMapper.map(roomEquipmentDto, RoomEquipment.class);
		return modelMapper.map(roomEquipmentRepository.save(roomEquipment), RoomEquipmentDto.class);
	}

	@Override
	@Transactional
	public RoomEquipmentDto updateRoomEquipment(RoomEquipmentDto roomEquipmentDto) {
		return saveRoomEquipment(roomEquipmentDto);
	}

	@Override
	@Transactional
	public void deleteRoomEquipmentById(Integer id) {
		roomEquipmentRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<RoomEquipmentDto> findAllRoomEquipments() {
		List<RoomEquipment> roomEquipments = roomEquipmentRepository.findAll();
		Type targetListType = new TypeToken<List<RoomEquipmentDto>>() {}.getType();
		List<RoomEquipmentDto> roomEquipmentDtos = modelMapper.map(roomEquipments, targetListType);
		return roomEquipmentDtos;
	}
	
	@Override
	@Transactional
	public List<RoomEquipmentDto> findRoomEquipmentsByroomId(Integer id) {
		List<RoomEquipment> roomEquipments = roomEquipmentRepository.findAllByRoomRoomId(id);
		Type targetListType = new TypeToken<List<RoomEquipmentDto>>() {}.getType();
		return modelMapper.map(roomEquipments, targetListType);
	}

	@Override
	@Transactional
	public List<RoomEquipmentDto> findAllRoomSEquipments(Integer id) {
		List<RoomEquipment> roomEquipments = roomEquipmentRepository.findAllByRoomRoomId(id);
		Type targetListType = new TypeToken<List<RoomEquipmentDto>>() {}.getType();
		List<RoomEquipmentDto> roomEquipmentDtos = modelMapper.map(roomEquipments, targetListType);
		return roomEquipmentDtos;
	}

	@Override
	@Transactional
	public List<RoomEquipmentDto> findAllRoomsWhereEquipments(Integer id) {
		List<RoomEquipment> roomEquipments = roomEquipmentRepository.findAllByEquipmentEquipmentId(id);
		Type targetListType = new TypeToken<List<RoomEquipmentDto>>() {}.getType();
		List<RoomEquipmentDto> roomEquipmentDtos = modelMapper.map(roomEquipments, targetListType);
		return roomEquipmentDtos;
	}

}

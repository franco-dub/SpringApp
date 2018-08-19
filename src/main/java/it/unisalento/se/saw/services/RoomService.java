package it.unisalento.se.saw.services;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.unisalento.se.saw.IService.RoomIService;
import it.unisalento.se.saw.domain.Room;
import it.unisalento.se.saw.dto.RoomDto;
import it.unisalento.se.saw.repo.RoomRepository;

@Service
public class RoomService implements RoomIService {

	private static final ModelMapper modelMapper = new ModelMapper();
	RoomRepository roomRepository;
	@Autowired
	public RoomService(RoomRepository roomRepository) {
		super();
		this.roomRepository = roomRepository;
	}

	@Override
	@Transactional
	public RoomDto findById(Integer id) {
		Room room = roomRepository.findById(id).get();
		RoomDto roomDto = modelMapper.map(room, RoomDto.class);
		return roomDto;
	}

	@Override
	@Transactional
	public void saveRoom(RoomDto roomDto) {
		Room room = modelMapper.map(roomDto, Room.class);
		roomRepository.save(room);
	}

	@Override
	@Transactional
	public void updateRoom(RoomDto roomDto) {
		saveRoom(roomDto);
	}

	@Override
	@Transactional
	public void deleteRoomById(Integer id) {
		roomRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<RoomDto> findAllRooms() {
		List<Room> rooms = roomRepository.findAll();
		Type targetListType = new TypeToken<List<RoomDto>>() {}.getType();
		List<RoomDto> roomDtos = modelMapper.map(rooms, targetListType);
		return roomDtos;
	}

}

package it.unisalento.se.saw.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.se.saw.domain.RoomEquipment;

@Repository
public interface RoomEquipmentRepository extends JpaRepository<RoomEquipment, Integer> {
	List<RoomEquipment> findAllByRoomRoomId(Integer roomId);
	List<RoomEquipment> findAllByEquipmentEquipmentId(Integer equipmentId);
}

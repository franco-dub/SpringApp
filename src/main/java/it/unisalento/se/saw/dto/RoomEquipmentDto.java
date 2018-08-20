package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class RoomEquipmentDto {

	private Integer roomEquipmentId;
	@NotNull
    private EquipmentDto equipment;
	@NotNull
    private RoomDto room;
    private String issue;
    @NotNull
    private byte work;
    
    public RoomEquipmentDto() {}

	public Integer getRoomEquipmentId() {
		return roomEquipmentId;
	}

	public void setRoomEquipmentId(Integer roomEquipmentId) {
		this.roomEquipmentId = roomEquipmentId;
	}

	public EquipmentDto getEquipment() {
		return equipment;
	}

	public void setEquipment(EquipmentDto equipment) {
		this.equipment = equipment;
	}

	public RoomDto getRoom() {
		return room;
	}

	public void setRoom(RoomDto room) {
		this.room = room;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public byte getWork() {
		return work;
	}

	public void setWork(byte work) {
		this.work = work;
	}
    
    
}

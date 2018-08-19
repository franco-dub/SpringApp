package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class RoomDto {

	private Integer roomId;
	@NotNull
    private String name;
    private String location;
    @NotNull
    private int capacity;
    
    public RoomDto() {}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
    
    
}

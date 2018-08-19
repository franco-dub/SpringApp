package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class EquipmentDto {

	private Integer equipmentId;
	@NotNull
    private String name;
    
    public EquipmentDto() {}

	public Integer getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}

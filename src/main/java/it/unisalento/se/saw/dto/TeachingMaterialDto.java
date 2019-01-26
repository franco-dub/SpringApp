package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import it.unisalento.se.saw.domain.Module;

public class TeachingMaterialDto {
    private Integer techingMaterialId;
    @NotNull
    private String fileName;
    @NotNull
    private String fileType;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss", timezone = "Europe/Rome")
    private Date created;
    @NotNull
    private long size;
    
    public TeachingMaterialDto() {}
    
    public TeachingMaterialDto(Integer teachingMaterialId, String fileName,
    		String fileType, Date created, long size) {
    	this.techingMaterialId = teachingMaterialId;
    	this.fileName = fileName;
    	this.fileType = fileType;
    	this.created = created;
    	this.size = size;
    }

	public Integer getTeachingMaterialId() {
		return techingMaterialId;
	}

	public void setTechingMaterialId(Integer techingMaterialId) {
		this.techingMaterialId = techingMaterialId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
    
}

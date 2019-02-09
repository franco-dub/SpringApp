package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TeachingMaterialDto {
    private Integer teachingMaterialId;
    @NotNull
    private String fileName;
    @NotNull
    private String fileType;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Europe/Rome")
    private Date created;
    @NotNull
    private long size;
    
    public TeachingMaterialDto() {}
    
    public TeachingMaterialDto(Integer teachingMaterialId, String fileName,
    		String fileType, Date created, long size) {
    	this.teachingMaterialId = teachingMaterialId;
    	this.fileName = fileName;
    	this.fileType = fileType;
    	this.created = created;
    	this.size = size;
    }

	public Integer getTeachingMaterialId() {
		return teachingMaterialId;
	}

	public void setTeachingMaterialId(Integer teachingMaterialId) {
		this.teachingMaterialId = teachingMaterialId;
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

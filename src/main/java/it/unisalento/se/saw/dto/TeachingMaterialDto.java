package it.unisalento.se.saw.dto;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

import it.unisalento.se.saw.domain.Module;

public class TeachingMaterialDto {
    private Integer techingMaterialId;
    @NotNull
    private ModuleDto module;
    @Lob
    private byte[] doc;
    @NotNull
    private String fileName;
    @NotNull
    private String fileType;
    
    public TeachingMaterialDto() {}

	public Integer getTechingMaterialId() {
		return techingMaterialId;
	}

	public void setTechingMaterialId(Integer techingMaterialId) {
		this.techingMaterialId = techingMaterialId;
	}

	public ModuleDto getModule() {
		return module;
	}

	public void setModule(ModuleDto module) {
		this.module = module;
	}

	public byte[] getDoc() {
		return doc;
	}

	public void setDoc(byte[] doc) {
		this.doc = doc;
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
    
}

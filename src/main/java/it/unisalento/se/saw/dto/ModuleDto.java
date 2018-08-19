package it.unisalento.se.saw.dto;

import javax.validation.constraints.NotNull;

public class ModuleDto {

    private Integer moduleId;
    @NotNull
    private CourseDto course;
    @NotNull
    private ProfessorDto professor;
    @NotNull
    private String title;
    @NotNull
    private int credits;
    @NotNull
    private String semester;
    @NotNull
    private int year;
    
    public ModuleDto() {}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}	

	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}

	public ProfessorDto getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDto professor) {
		this.professor = professor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
}

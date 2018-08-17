package it.unisalento.se.saw.dto;

public class LoginDto {
	public enum Type {
		STUDENT, 
		PROFESSOR, 
		SECRETARY,
		NOT_FOUND
	};
	private StudentDto student;
	private ProfessorDto professor;
	private SecretaryDto secretary;
	private Type type;
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public StudentDto getStudent() {
		return student;
	}
	public void setStudent(StudentDto student) {
		this.student = student;
	}
	public ProfessorDto getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorDto professor) {
		this.professor = professor;
	}
	public SecretaryDto getSecretary() {
		return secretary;
	}
	public void setSecretary(SecretaryDto secretary) {
		this.secretary = secretary;
	}
	
	
}

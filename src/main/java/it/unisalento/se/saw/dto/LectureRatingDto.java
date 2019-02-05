package it.unisalento.se.saw.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class LectureRatingDto {

    private Integer lectureRatingId;
    @NotNull
    private CalendarDto calendar;
    @NotNull
    private StudentDto student;
    @NotNull
    private String rate;
    @NotNull
    private Date date;
    @NotNull
    private String note;
    
    public LectureRatingDto() {}

	public Integer getLectureRatingId() {
		return lectureRatingId;
	}

	public void setLectureRatingId(Integer lectureRatingId) {
		this.lectureRatingId = lectureRatingId;
	}

	public CalendarDto getCalendar() {
		return calendar;
	}

	public void setCalendar(CalendarDto calendar) {
		this.calendar = calendar;
	}

	public StudentDto getStudent() {
		return student;
	}

	public void setStudent(StudentDto student) {
		this.student = student;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
    
    
}

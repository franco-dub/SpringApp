package it.unisalento.se.saw.domain;
// Generated Aug 24, 2018, 10:00:52 AM by Hibernate Tools 5.2.0.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Calendar generated by hbm2java
 */
@Entity
@Table(name="calendar"
    ,catalog="uni"
)
public class Calendar  implements java.io.Serializable {


     private Integer calendarId;
     private Module module;
     private Room room;
     private Date startTime;
     private Date endTime;
     private Date date;
     private String day;
     private String type;
     @JsonIgnore
     private Set<LectureRating> lectureRatings = new HashSet<LectureRating>(0);
     @JsonIgnore
     private Set<StudentExam> studentExams = new HashSet<StudentExam>(0);

    public Calendar() {
    }

	
    public Calendar(Module module, Room room, Date startTime, Date endTime, Date date, String day, String type) {
        this.module = module;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.day = day;
        this.type = type;
    }
    public Calendar(Module module, Room room, Date startTime, Date endTime, Date date, String day, String type, Set<LectureRating> lectureRatings, Set<StudentExam> studentExams) {
       this.module = module;
       this.room = room;
       this.startTime = startTime;
       this.endTime = endTime;
       this.date = date;
       this.day = day;
       this.type = type;
       this.lectureRatings = lectureRatings;
       this.studentExams = studentExams;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="calendar_id", unique=true, nullable=false)
    public Integer getCalendarId() {
        return this.calendarId;
    }
    
    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="module_module_id", nullable=false)
    public Module getModule() {
        return this.module;
    }
    
    public void setModule(Module module) {
        this.module = module;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_room_id", nullable=false)
    public Room getRoom() {
        return this.room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="start_time", nullable=false, length=8)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="end_time", nullable=false, length=8)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false, length=10)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="day", nullable=false, length=9)
    public String getDay() {
        return this.day;
    }
    
    public void setDay(String day) {
        this.day = day;
    }

    
    @Column(name="type", nullable=false, length=8)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="calendar")
    public Set<LectureRating> getLectureRatings() {
        return this.lectureRatings;
    }
    
    public void setLectureRatings(Set<LectureRating> lectureRatings) {
        this.lectureRatings = lectureRatings;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="calendar")
    public Set<StudentExam> getStudentExams() {
        return this.studentExams;
    }
    
    public void setStudentExams(Set<StudentExam> studentExams) {
        this.studentExams = studentExams;
    }




}



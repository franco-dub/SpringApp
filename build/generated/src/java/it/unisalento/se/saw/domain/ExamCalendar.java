package it.unisalento.se.saw.domain;
// Generated Aug 20, 2018, 1:46:40 PM by Hibernate Tools 5.2.0.Final


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

/**
 * ExamCalendar generated by hbm2java
 */
@Entity
@Table(name="exam_calendar"
    ,catalog="uni"
)
public class ExamCalendar  implements java.io.Serializable {


     private Integer examCalendarId;
     private Module module;
     private Room room;
     private Date date;
     private Date startTime;
     private Date endTime;
     private String day;
     private Set<StudentExam> studentExams = new HashSet<StudentExam>(0);

    public ExamCalendar() {
    }

	
    public ExamCalendar(Module module, Room room, Date date, Date startTime, Date endTime, String day) {
        this.module = module;
        this.room = room;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
    }
    public ExamCalendar(Module module, Room room, Date date, Date startTime, Date endTime, String day, Set<StudentExam> studentExams) {
       this.module = module;
       this.room = room;
       this.date = date;
       this.startTime = startTime;
       this.endTime = endTime;
       this.day = day;
       this.studentExams = studentExams;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="exam_calendar_id", unique=true, nullable=false)
    public Integer getExamCalendarId() {
        return this.examCalendarId;
    }
    
    public void setExamCalendarId(Integer examCalendarId) {
        this.examCalendarId = examCalendarId;
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

    @Temporal(TemporalType.DATE)
    @Column(name="date", nullable=false, length=10)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
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

    
    @Column(name="day", nullable=false, length=9)
    public String getDay() {
        return this.day;
    }
    
    public void setDay(String day) {
        this.day = day;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="examCalendar")
    public Set<StudentExam> getStudentExams() {
        return this.studentExams;
    }
    
    public void setStudentExams(Set<StudentExam> studentExams) {
        this.studentExams = studentExams;
    }




}


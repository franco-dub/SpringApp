package it.unisalento.se.saw.domain;
// Generated Aug 2, 2018, 5:19:14 PM by Hibernate Tools 5.2.0.Final


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ClassDayOfWeek generated by hbm2java
 */
@Entity
@Table(name="class_day_of_week"
    ,catalog="mydb"
)
public class ClassDayOfWeek  implements java.io.Serializable {


     private Integer classDayId;
     private Date endTime;
     private Date startTime;
     private int dayOfWeekDayId;
     private int lectureLectureId;
     private int roomRoomId;

    public ClassDayOfWeek() {
    }

    public ClassDayOfWeek(Date endTime, Date startTime, int dayOfWeekDayId, int lectureLectureId, int roomRoomId) {
       this.endTime = endTime;
       this.startTime = startTime;
       this.dayOfWeekDayId = dayOfWeekDayId;
       this.lectureLectureId = lectureLectureId;
       this.roomRoomId = roomRoomId;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="class_day_id", unique=true, nullable=false)
    public Integer getClassDayId() {
        return this.classDayId;
    }
    
    public void setClassDayId(Integer classDayId) {
        this.classDayId = classDayId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time", nullable=false, length=19)
    public Date getEndTime() {
        return this.endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name="start_time", nullable=false, length=8)
    public Date getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    
    @Column(name="day_of_week_day_id", nullable=false)
    public int getDayOfWeekDayId() {
        return this.dayOfWeekDayId;
    }
    
    public void setDayOfWeekDayId(int dayOfWeekDayId) {
        this.dayOfWeekDayId = dayOfWeekDayId;
    }

    
    @Column(name="lecture_lecture_id", nullable=false)
    public int getLectureLectureId() {
        return this.lectureLectureId;
    }
    
    public void setLectureLectureId(int lectureLectureId) {
        this.lectureLectureId = lectureLectureId;
    }

    
    @Column(name="room_room_id", nullable=false)
    public int getRoomRoomId() {
        return this.roomRoomId;
    }
    
    public void setRoomRoomId(int roomRoomId) {
        this.roomRoomId = roomRoomId;
    }




}



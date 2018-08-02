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
 * Ticket generated by hbm2java
 */
@Entity
@Table(name="ticket"
    ,catalog="mydb"
)
public class Ticket  implements java.io.Serializable {


     private Integer ticketId;
     private String comment;
     private Date date;
     private String description;
     private String status;
     private int professorProfessorId;
     private int roomRoomId;
     private int secretarySecretaryId;

    public Ticket() {
    }

	
    public Ticket(Date date, String description, int professorProfessorId, int roomRoomId, int secretarySecretaryId) {
        this.date = date;
        this.description = description;
        this.professorProfessorId = professorProfessorId;
        this.roomRoomId = roomRoomId;
        this.secretarySecretaryId = secretarySecretaryId;
    }
    public Ticket(String comment, Date date, String description, String status, int professorProfessorId, int roomRoomId, int secretarySecretaryId) {
       this.comment = comment;
       this.date = date;
       this.description = description;
       this.status = status;
       this.professorProfessorId = professorProfessorId;
       this.roomRoomId = roomRoomId;
       this.secretarySecretaryId = secretarySecretaryId;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ticket_id", unique=true, nullable=false)
    public Integer getTicketId() {
        return this.ticketId;
    }
    
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    
    @Column(name="comment", length=200)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable=false, length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    
    @Column(name="description", nullable=false, length=200)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="status", length=10)
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    
    @Column(name="professor_professor_id", nullable=false)
    public int getProfessorProfessorId() {
        return this.professorProfessorId;
    }
    
    public void setProfessorProfessorId(int professorProfessorId) {
        this.professorProfessorId = professorProfessorId;
    }

    
    @Column(name="room_room_id", nullable=false)
    public int getRoomRoomId() {
        return this.roomRoomId;
    }
    
    public void setRoomRoomId(int roomRoomId) {
        this.roomRoomId = roomRoomId;
    }

    
    @Column(name="secretary_secretary_id", nullable=false)
    public int getSecretarySecretaryId() {
        return this.secretarySecretaryId;
    }
    
    public void setSecretarySecretaryId(int secretarySecretaryId) {
        this.secretarySecretaryId = secretarySecretaryId;
    }




}



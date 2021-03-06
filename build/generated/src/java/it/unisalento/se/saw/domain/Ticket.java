package it.unisalento.se.saw.domain;
// Generated Aug 23, 2018, 4:34:20 PM by Hibernate Tools 5.2.0.Final


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Ticket generated by hbm2java
 */
@Entity
@Table(name="ticket"
    ,catalog="uni"
)
public class Ticket  implements java.io.Serializable {


     private Integer ticketId;
     private Professor professor;
     private Room room;
     private Secretary secretary;
     private String title;
     private Date date;
     private Date lastModified;
     private String description;
     private String status;
     private String comment;

    public Ticket() {
    }

	
    public Ticket(Professor professor, Room room, String title, Date date, String description) {
        this.professor = professor;
        this.room = room;
        this.title = title;
        this.date = date;
        this.description = description;
    }
    public Ticket(Professor professor, Room room, Secretary secretary, String title, Date date, Date lastModified, String description, String status, String comment) {
       this.professor = professor;
       this.room = room;
       this.secretary = secretary;
       this.title = title;
       this.date = date;
       this.lastModified = lastModified;
       this.description = description;
       this.status = status;
       this.comment = comment;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ticket_id", unique=true, nullable=false)
    public Integer getTicketId() {
        return this.ticketId;
    }
    
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="professor_professor_id", nullable=false)
    public Professor getProfessor() {
        return this.professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="room_room_id", nullable=false)
    public Room getRoom() {
        return this.room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="secretary_secretary_id")
    public Secretary getSecretary() {
        return this.secretary;
    }
    
    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    
    @Column(name="title", nullable=false, length=45)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date", nullable=false, length=19)
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modified", length=19)
    public Date getLastModified() {
        return this.lastModified;
    }
    
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
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

    
    @Column(name="comment", length=200)
    public String getComment() {
        return this.comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }




}



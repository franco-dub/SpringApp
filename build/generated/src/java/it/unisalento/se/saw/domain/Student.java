package it.unisalento.se.saw.domain;
// Generated Aug 2, 2018, 5:57:08 PM by Hibernate Tools 5.2.0.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Student generated by hbm2java
 */
@Entity
@Table(name="student"
    ,catalog="uni"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Student  implements java.io.Serializable {


     private Integer studentId;
     private Course course;
     private Person person;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
     private Date registrationDate;
     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Europe/Rome")
     private Date graduationDate;
     private Set<StudentExam> studentExams = new HashSet<StudentExam>(0);

    public Student() {
    }

	
    public Student(Course course, Person person, Date registrationDate) {
        this.course = course;
        this.person = person;
        this.registrationDate = registrationDate;
    }
    public Student(Course course, Person person, Date registrationDate, Date graduationDate, Set<StudentExam> studentExams) {
       this.course = course;
       this.person = person;
       this.registrationDate = registrationDate;
       this.graduationDate = graduationDate;
       this.studentExams = studentExams;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="student_id", unique=true, nullable=false)
    public Integer getStudentId() {
        return this.studentId;
    }
    
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="course_course_id", nullable=false)
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }

@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="person_person_id", nullable=false)
    public Person getPerson() {
        return this.person;
    }
    
    public void setPerson(Person person) {
        this.person = person;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="registration_date", nullable=false, length=10)
    public Date getRegistrationDate() {
        return this.registrationDate;
    }
    
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="graduation_date", length=10)
    public Date getGraduationDate() {
        return this.graduationDate;
    }
    
    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="student")
@JsonIgnore
    public Set<StudentExam> getStudentExams() {
        return this.studentExams;
    }
    
    public void setStudentExams(Set<StudentExam> studentExams) {
        this.studentExams = studentExams;
    }




}


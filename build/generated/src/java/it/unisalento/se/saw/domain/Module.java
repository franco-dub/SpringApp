package it.unisalento.se.saw.domain;
// Generated Aug 23, 2018, 4:34:20 PM by Hibernate Tools 5.2.0.Final


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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Module generated by hbm2java
 */
@Entity
@Table(name="module"
    ,catalog="uni"
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Module  implements java.io.Serializable {


     private Integer moduleId;
     private Course course;
     private Professor professor;
     private String title;
     private int credits;
     private String semester;
     private int year;
     @JsonIgnore
     private Set<TeachingMaterial> teachingMaterials = new HashSet<TeachingMaterial>(0);
     @JsonIgnore
     private Set<Calendar> calendars = new HashSet<Calendar>(0);

    public Module() {
    }

	
    public Module(Course course, Professor professor, String title, int credits, String semester, int year) {
        this.course = course;
        this.professor = professor;
        this.title = title;
        this.credits = credits;
        this.semester = semester;
        this.year = year;
    }
    public Module(Course course, Professor professor, String title, int credits, String semester, int year, Set<TeachingMaterial> teachingMaterials, Set<Calendar> calendars) {
       this.course = course;
       this.professor = professor;
       this.title = title;
       this.credits = credits;
       this.semester = semester;
       this.year = year;
       this.teachingMaterials = teachingMaterials;
       this.calendars = calendars;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="module_id", unique=true, nullable=false)
    public Integer getModuleId() {
        return this.moduleId;
    }
    
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="course_course_id", nullable=false)
    public Course getCourse() {
        return this.course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="professor_professor_id", nullable=false)
    public Professor getProfessor() {
        return this.professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    
    @Column(name="title", nullable=false, length=45)
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    
    @Column(name="credits", nullable=false)
    public int getCredits() {
        return this.credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }

    
    @Column(name="semester", nullable=false, length=45)
    public String getSemester() {
        return this.semester;
    }
    
    public void setSemester(String semester) {
        this.semester = semester;
    }

    
    @Column(name="year", nullable=false)
    public int getYear() {
        return this.year;
    }
    
    public void setYear(int year) {
        this.year = year;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="module")
    public Set<TeachingMaterial> getTeachingMaterials() {
        return this.teachingMaterials;
    }
    
    public void setTeachingMaterials(Set<TeachingMaterial> teachingMaterials) {
        this.teachingMaterials = teachingMaterials;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="module")
    public Set<Calendar> getCalendars() {
        return this.calendars;
    }
    
    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }




}



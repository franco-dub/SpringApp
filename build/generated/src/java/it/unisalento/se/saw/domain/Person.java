package it.unisalento.se.saw.domain;
// Generated Feb 1, 2019, 12:19:39 PM by Hibernate Tools 5.2.0.Final


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Person generated by hbm2java
 */
@Entity
@Table(name="person"
    ,catalog="uni"
)
public class Person  implements java.io.Serializable {


     private Integer personId;
     private String firstName;
     private String lastName;
     private String email;
     private String phone;
     private Date dateOfBirth;
     private String gender;
     private String address;
     private String password;
     private Set<Secretary> secretaries = new HashSet<Secretary>(0);
     private Set<Professor> professors = new HashSet<Professor>(0);
     private Set<Student> students = new HashSet<Student>(0);

    public Person() {
    }

	
    public Person(String firstName, String lastName, String email, String phone, Date dateOfBirth, String gender, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.password = password;
    }
    public Person(String firstName, String lastName, String email, String phone, Date dateOfBirth, String gender, String address, String password, Set<Secretary> secretaries, Set<Professor> professors, Set<Student> students) {
       this.firstName = firstName;
       this.lastName = lastName;
       this.email = email;
       this.phone = phone;
       this.dateOfBirth = dateOfBirth;
       this.gender = gender;
       this.address = address;
       this.password = password;
       this.secretaries = secretaries;
       this.professors = professors;
       this.students = students;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="person_id", unique=true, nullable=false)
    public Integer getPersonId() {
        return this.personId;
    }
    
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    
    @Column(name="first_name", nullable=false, length=45)
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    
    @Column(name="last_name", nullable=false, length=45)
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    
    @Column(name="email", nullable=false, length=45)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="phone", nullable=false, length=45)
    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="date_of_birth", nullable=false, length=10)
    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    
    @Column(name="gender", nullable=false, length=1)
    public String getGender() {
        return this.gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    
    @Column(name="address", length=45)
    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    
    @Column(name="password", nullable=false, length=45)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
    public Set<Secretary> getSecretaries() {
        return this.secretaries;
    }
    
    public void setSecretaries(Set<Secretary> secretaries) {
        this.secretaries = secretaries;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
    public Set<Professor> getProfessors() {
        return this.professors;
    }
    
    public void setProfessors(Set<Professor> professors) {
        this.professors = professors;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
    public Set<Student> getStudents() {
        return this.students;
    }
    
    public void setStudents(Set<Student> students) {
        this.students = students;
    }




}



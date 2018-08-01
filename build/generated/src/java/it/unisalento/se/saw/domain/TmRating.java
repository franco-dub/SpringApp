package it.unisalento.se.saw.domain;
// Generated Aug 1, 2018, 2:16:06 PM by Hibernate Tools 5.2.0.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TmRating generated by hbm2java
 */
@Entity
@Table(name="tm_rating"
    ,catalog="mydb"
)
public class TmRating  implements java.io.Serializable {


     private Integer tmRatingId;
     private String rate;
     private int teachingMaterialTechingMaterialId;

    public TmRating() {
    }

    public TmRating(String rate, int teachingMaterialTechingMaterialId) {
       this.rate = rate;
       this.teachingMaterialTechingMaterialId = teachingMaterialTechingMaterialId;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="tm_rating_id", unique=true, nullable=false)
    public Integer getTmRatingId() {
        return this.tmRatingId;
    }
    
    public void setTmRatingId(Integer tmRatingId) {
        this.tmRatingId = tmRatingId;
    }

    
    @Column(name="rate", nullable=false, length=2)
    public String getRate() {
        return this.rate;
    }
    
    public void setRate(String rate) {
        this.rate = rate;
    }

    
    @Column(name="teaching_material_teching_material_id", nullable=false)
    public int getTeachingMaterialTechingMaterialId() {
        return this.teachingMaterialTechingMaterialId;
    }
    
    public void setTeachingMaterialTechingMaterialId(int teachingMaterialTechingMaterialId) {
        this.teachingMaterialTechingMaterialId = teachingMaterialTechingMaterialId;
    }




}



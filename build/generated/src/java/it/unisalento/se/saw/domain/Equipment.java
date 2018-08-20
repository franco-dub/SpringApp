package it.unisalento.se.saw.domain;
// Generated Aug 20, 2018, 1:46:40 PM by Hibernate Tools 5.2.0.Final


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

/**
 * Equipment generated by hbm2java
 */
@Entity
@Table(name="equipment"
    ,catalog="uni"
)
public class Equipment  implements java.io.Serializable {


     private Integer equipmentId;
     private String name;
     private Set<RoomEquipment> roomEquipments = new HashSet<RoomEquipment>(0);

    public Equipment() {
    }

	
    public Equipment(String name) {
        this.name = name;
    }
    public Equipment(String name, Set<RoomEquipment> roomEquipments) {
       this.name = name;
       this.roomEquipments = roomEquipments;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="equipment_id", unique=true, nullable=false)
    public Integer getEquipmentId() {
        return this.equipmentId;
    }
    
    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    
    @Column(name="name", nullable=false, length=45)
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="equipment")
    public Set<RoomEquipment> getRoomEquipments() {
        return this.roomEquipments;
    }
    
    public void setRoomEquipments(Set<RoomEquipment> roomEquipments) {
        this.roomEquipments = roomEquipments;
    }




}



package kg.nurtelecom.registration.common.entity.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "region")
    private List<District> districtEntities;

    public Region() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<District> getDistrictEntities() {
        return districtEntities;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistrictEntities(List<District> districtEntities) {
        this.districtEntities = districtEntities;
    }
}

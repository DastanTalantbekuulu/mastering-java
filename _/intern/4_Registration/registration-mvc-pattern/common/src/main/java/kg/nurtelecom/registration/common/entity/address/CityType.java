package kg.nurtelecom.registration.common.entity.address;

import jakarta.persistence.*;

@Entity
@Table(name = "city_type")
public class CityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "requires_region", nullable = false)
    private boolean requiresRegion;

    @Column(name = "requires_district", nullable = false)
    private boolean requiresDistrict;

    public CityType() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isRequiresRegion() {
        return requiresRegion;
    }

    public boolean isRequiresDistrict() {
        return requiresDistrict;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequiresRegion(boolean requiresRegion) {
        this.requiresRegion = requiresRegion;
    }

    public void setRequiresDistrict(boolean requiresDistrict) {
        this.requiresDistrict = requiresDistrict;
    }
}

package kg.nurtelecom.registration.common.entity.address;

import jakarta.persistence.*;
import kg.nurtelecom.registration.common.payload.response.address.DistrictResponse;

@Entity
@Table(name = "district")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    public District() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Region getRegion() {
        return region;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public DistrictResponse toModel() {
        return new DistrictResponse(
                id,
                name,
                region.getId(),
                region.getName()
        );
    }
}

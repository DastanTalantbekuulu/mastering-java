package kg.nurtelecom.registration.common.entity.address;

import jakarta.persistence.*;
import kg.nurtelecom.registration.common.payload.response.address.CityResponse;

@Entity
@Table(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_type_id")
    private CityType cityType;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private District district;

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CityType getCityType() {
        return cityType;
    }

    public Region getRegion() {
        return region;
    }

    public District getDistrict() {
        return district;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public CityResponse toModel() {
        String regionName = region != null? region.getName() : null;
        Integer regionId = region != null? region.getId() : null;
        String districtName = district != null? district.getName() : null;
        Integer districtId = district != null? district.getId() : null;

        return new CityResponse(
                id,
                name,
                cityType.getId(),
                cityType.getName(),
                regionId,
                regionName,
                districtId,
                districtName);
    }
}

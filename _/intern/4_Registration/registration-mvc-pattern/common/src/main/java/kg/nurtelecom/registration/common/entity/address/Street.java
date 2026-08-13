package kg.nurtelecom.registration.common.entity.address;

import jakarta.persistence.*;
import kg.nurtelecom.registration.common.payload.response.address.StreetResponse;

@Entity
@Table(name = "street")
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public Street() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public StreetResponse toModel() {
        return new StreetResponse(
                id,
                name,
                city.getId(),
                city.getName()
        );
    }
}

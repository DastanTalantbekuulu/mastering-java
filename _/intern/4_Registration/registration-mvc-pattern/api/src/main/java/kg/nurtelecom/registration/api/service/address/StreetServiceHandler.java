package kg.nurtelecom.registration.api.service.address;

import jakarta.persistence.EntityNotFoundException;
import kg.nurtelecom.registration.api.repository.jpa.address.CityRepository;
import kg.nurtelecom.registration.api.repository.jpa.address.StreetRepository;
import kg.nurtelecom.registration.common.entity.address.City;
import kg.nurtelecom.registration.common.entity.address.Street;
import kg.nurtelecom.registration.common.payload.request.address.StreetRequest;
import kg.nurtelecom.registration.common.payload.response.address.StreetResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StreetServiceHandler implements StreetService {
    private final StreetRepository streetRepository;
    private final CityRepository cityRepository;

    public StreetServiceHandler(StreetRepository streetRepository, CityRepository cityRepository) {
        this.streetRepository = streetRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<StreetResponse> getAllStreets() {
        List<Street> streetList = streetRepository.findAll();
        List<StreetResponse> streetResponseList = new ArrayList<>();
        for(Street street : streetList) {
            streetResponseList.add(street.toModel());
        }
        return streetResponseList;
    }

    @Override
    public StreetResponse getStreetById(Integer id) {
        Street street = streetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Улица с ID " + id + " не найдена."));
        return street.toModel();
    }

    @Override
    public StreetResponse saveStreet(StreetRequest streetRequest) {
        City city = cityRepository.findById(streetRequest.cityId())
                .orElseThrow(() -> new EntityNotFoundException("Город с ID " + streetRequest.cityId() + " не найден."));
        Street street = new Street();
        street.setName(streetRequest.name());
        street.setCity(city);
        street = streetRepository.save(street);
        return street.toModel();
    }

    @Override
    public StreetResponse updateStreet(Integer id, StreetRequest streetRequest) {
        Street street = streetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Улица с ID " + id + " не найдена."));
        City city = cityRepository.findById(streetRequest.cityId())
                .orElseThrow(() -> new EntityNotFoundException("Город с ID " + streetRequest.cityId() + " не найден."));
        street.setName(streetRequest.name());
        street.setCity(city);
        street = streetRepository.save(street);
        return street.toModel();
    }

    @Override
    public void deleteStreetById(Integer id) {
        if(!streetRepository.existsById(id)) {
            throw new EntityNotFoundException("Улица с ID " + id + " не найдена.");
        }
        streetRepository.deleteById(id);
    }
}

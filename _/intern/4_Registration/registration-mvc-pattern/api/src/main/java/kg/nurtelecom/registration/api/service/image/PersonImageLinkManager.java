package kg.nurtelecom.registration.api.service.image;


import kg.nurtelecom.registration.api.service.person.PersonService;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.entity.ImageData;
import kg.nurtelecom.registration.common.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonImageLinkManager implements PersonImageLinkService {

    private final PersonService clientService;
    private final ImageService imageService;

    @Value("${app.image-client-path}")
    private String clientImagesPath;

    @Value("${app.image-staff-path}")
    private String staffImagesPath;

    public PersonImageLinkManager(PersonService clientService, ImageService imageService) {
        this.clientService = clientService;
        this.imageService = imageService;
    }

    @Transactional
    public Person save(MultipartFile passportFront, MultipartFile passportBack, MultipartFile passportFace, String personData) {
        Person person = clientService.save(personData);
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Клиент не создан");
        }
        String imagePath = person.getRole().equals(Role.CLIENT) ? clientImagesPath : staffImagesPath;
        List<ImageData> images = imageService.uploadImagesToFileSystem(
                passportFront, passportBack, passportFace, person, imagePath);
        List<ImageData> existingImages = person.getImages();

        if (existingImages == null) {
            existingImages = new ArrayList<>();
            person.setImages(existingImages);
        }

        existingImages.clear();
        existingImages.addAll(images);

        person = clientService.save(person);

        return person;
    }


}

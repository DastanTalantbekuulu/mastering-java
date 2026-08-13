package kg.nurtelecom.registration.api.service.image;

import kg.nurtelecom.registration.common.entity.Person;
import org.springframework.web.multipart.MultipartFile;

public interface PersonImageLinkService {
    Person save(MultipartFile passportFront, MultipartFile passportBack, MultipartFile passportFace, String clientData);
}

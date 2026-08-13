package kg.nurtelecom.registration.api.service.image;

import kg.nurtelecom.registration.common.entity.ImageData;
import kg.nurtelecom.registration.common.entity.Person;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageData> uploadImagesToFileSystem(MultipartFile passportFront, MultipartFile passportBack,
                                             MultipartFile passportFace, Person person, String imagePath);

    byte[] getPassportImageFromFileSystem(String imagePath);

    void validateImageFormat(List<MultipartFile> images);

    Resource getImage(String clientType, String imageType, String fileName) throws IOException;

    public String getImageContentType(String fileName);

    void updateImages(Long id, MultipartFile passportFace, MultipartFile passportBack, MultipartFile passportFront);
}

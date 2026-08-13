package kg.nurtelecom.registration.api.repository.jpa;

import kg.nurtelecom.registration.common.entity.ImageData;
import kg.nurtelecom.registration.common.enums.ImageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageData, Long> {

    ImageData findByFilePath(String imagePath);

    ImageData findByImageType(ImageType imageType);

    ImageData findByPersonIdAndImageType(Long id, ImageType imageType);

}

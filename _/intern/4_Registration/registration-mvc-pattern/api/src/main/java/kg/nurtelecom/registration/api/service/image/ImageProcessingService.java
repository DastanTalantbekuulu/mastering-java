package kg.nurtelecom.registration.api.service.image;

import kg.nurtelecom.registration.api.repository.jpa.PersonRepository;
import org.springframework.core.io.Resource;
import kg.nurtelecom.registration.api.repository.jpa.ImageRepository;
import kg.nurtelecom.registration.common.entity.ImageData;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.ImageType;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageProcessingService implements ImageService {

    private final ImageRepository imageRepository;
    private final PersonRepository personRepository;

    @Value("${app.image-extension}")
    private String fileExtension;

    @Value("${app.image-client-path}")
    private String clientImagesPath;

    @Value("${app.image-staff-path}")
    private String staffImagesPath;

    public ImageProcessingService(ImageRepository imageRepository, PersonRepository personRepository) {
        this.imageRepository = imageRepository;
        this.personRepository = personRepository;
    }

    public byte[] getPassportImageFromFileSystem(String imagePath) {
        try {
            ImageData imageData = imageRepository.findByFilePath(imagePath);
            if (imageData == null) {
                throw new IllegalArgumentException("No image metadata found for path: " + imagePath);
            }

            Path path = Paths.get(imageData.getFilePath());
            if (Files.exists(path)) {
                return Files.readAllBytes(path);
            } else {
                throw new IllegalStateException("Image file not found at path: " + imagePath);
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<ImageData> uploadImagesToFileSystem(MultipartFile passportFront,
                                                    MultipartFile passportBack,
                                                    MultipartFile passportFace,
                                                    Person person,
                                                    String imagePath) {
        List<ImageData> images = new ArrayList<>();
        images.add(uploadImage(passportFront, "front", person, imagePath));
        images.add(uploadImage(passportBack, "back", person, imagePath));
        images.add(uploadImage(passportFace, "face", person, imagePath));
        return images;
    }

    private ImageData uploadImage(MultipartFile image, String subdirectory, Person person, String imagePath) {
        try {

            String currentImagePath = imagePath + generateDirectoryName(subdirectory);
            File directory = new File(currentImagePath);

            if (!directory.exists() && !directory.mkdirs()) {
                throw new IllegalStateException("Failed to create directory: " + currentImagePath);
            }

            String newFileName = generateFileName() + fileExtension;

            BufferedImage originalImage = ImageIO.read(image.getInputStream());

            float compressionQuality = 0.5f;

            File outputFile = new File(currentImagePath + "/" + newFileName);

            Thumbnails.of(originalImage)
                    .scale(1)
                    .outputQuality(compressionQuality)
                    .toFile(outputFile);

            ImageData imageData = new ImageData();
            imageData.setFileName(newFileName);
            imageData.setFilePath(outputFile.getAbsolutePath());
            imageData.setImageType(ImageType.valueOf(subdirectory.toUpperCase()));
            imageData.setPerson(person);

            imageRepository.save(imageData);
            return imageData;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload and compress image: " + e.getMessage(), e);
        }
    }

    private String generateDirectoryName(String subdirectory) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "/" + subdirectory + "/" + LocalDateTime.now().format(formatter) + "/";
    }

    private String generateFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }
    @Override
    public void validateImageFormat(List<MultipartFile> images) {
        List<String> allowedMimeTypes = List.of("image/jpeg", "image/png");
        for (MultipartFile image : images) {
            String mimeType = image.getContentType();
            if (!allowedMimeTypes.contains(mimeType)) {
                throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                        "Недопустимый формат файла: " + mimeType + ". Разрешены только JPEG и PNG.");
            }
        }
    }

    public Resource getImage(String clientType, String imageType, String fileName) throws IOException {
        String datePart = extractDateFromFileName(fileName);
        Path imagePath;
        if (clientType.equals("client")) {
            imagePath = Paths.get(clientImagesPath, imageType, datePart, fileName);
        } else {
            imagePath = Paths.get(staffImagesPath, imageType, datePart, fileName);
        }

        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        } else {
            throw new FileNotFoundException("Файл не найден: " + imagePath);
        }
    }

    @Override
    public String getImageContentType(String fileName) {
        if (fileName.toLowerCase().endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileName);
        }
    }

    private String extractDateFromFileName(String fileName) {
        return fileName.substring(0, 10);
    }

    @Override
    public void updateImages(Long id, MultipartFile passportFace, MultipartFile passportBack, MultipartFile passportFront) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found"));

        if (passportFace != null) {
            updateImageForPerson(person, passportFace, ImageType.FACE);
        }
        if (passportBack != null) {
            updateImageForPerson(person, passportBack, ImageType.BACK);
        }
        if (passportFront != null) {
            updateImageForPerson(person, passportFront, ImageType.FRONT);
        }
    }

    private void updateImageForPerson(Person person, MultipartFile image, ImageType imageType) {
        try {
            ImageData existingImage = imageRepository.findByPersonIdAndImageType(person.getId(), imageType);
            if (existingImage != null) {

                StringBuilder existingFilePath = new StringBuilder(existingImage.getFilePath());

                StringBuilder fileName = new StringBuilder(existingImage.getFileName());

                StringBuilder currentImagePath = new StringBuilder(existingFilePath.substring(0, existingFilePath.length() - fileName.length()));                System.out.println("Путь к директории: " + currentImagePath);

                File existingFile = new File(existingFilePath.toString());
                if (existingFile.exists()) {
                    if (!existingFile.delete()) {
                        throw new IllegalStateException("Failed to delete: " + existingFilePath);
                    }
                }

                BufferedImage originalImage = ImageIO.read(image.getInputStream());
                float compressionQuality = 0.5f;  // Параметр сжатия

                File outputFile = new File(currentImagePath.append(File.separator).append(fileName).toString());

                Thumbnails.of(originalImage)
                        .scale(1)
                        .outputQuality(compressionQuality)
                        .toFile(outputFile);

                existingImage.setFilePath(outputFile.getAbsolutePath());
                imageRepository.save(existingImage);
            } else {
                throw new IllegalStateException("Image not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to update the image: " + e.getMessage(), e);
        }
    }


}

package kg.nurtelecom.registration.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kg.nurtelecom.registration.common.enums.ImageType;

@Entity
@Table(name = "image_data")
public class ImageData {

    @Id
    @GeneratedValue
    Long id;

    @Column(name = "file_name")
    String fileName;

    @Column(name = "file_path")
    String filePath;

    @Column(name = "image_type")
    ImageType imageType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id", nullable = false)
    Person person;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public Person getPerson() {
        return person;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

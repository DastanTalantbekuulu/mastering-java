package kg.nurtelecom.registration.api.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kg.nurtelecom.registration.api.service.person.PersonService;
import kg.nurtelecom.registration.api.service.export.ExcelExportService;
import kg.nurtelecom.registration.api.service.image.ImageService;
import kg.nurtelecom.registration.api.service.image.PersonImageLinkService;
import kg.nurtelecom.registration.common.entity.Person;
import kg.nurtelecom.registration.common.enums.Role;
import kg.nurtelecom.registration.common.enums.Status;
import org.springframework.core.io.Resource;
import kg.nurtelecom.registration.common.payload.request.PersonRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
public class PersonController {

    private final PersonService clientService;
    private final ExcelExportService excelExportService;
    private final ImageService imageService;
    private final PersonImageLinkService personImageLinkService;

    public PersonController(PersonService clientService, ExcelExportService excelExportService, ImageService imageService, PersonImageLinkService personImageLinkService) {
        this.clientService = clientService;
        this.excelExportService = excelExportService;
        this.imageService = imageService;
        this.personImageLinkService = personImageLinkService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Person>> getAllUsers() {
        List<Person> personList = clientService.getAllClients();
        return new ResponseEntity<>(personList, HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable @Min(1) Long id) {
        Person person = clientService.findClientById(id);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @GetMapping("/images/{clientType}/{imageType}/{fileName}")
    public ResponseEntity<Resource> getClientImage(
            @PathVariable String clientType,
            @PathVariable String imageType,
            @PathVariable String fileName
    ) throws IOException {
        Resource resource = imageService.getImage(clientType, imageType, fileName);
        String contentType = imageService.getImageContentType(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/image")
    public ResponseEntity<byte[]> getPassportImageByPath(@RequestParam(name = "image_path") String imagePath) {
        byte[] image = imageService.getPassportImageFromFileSystem(imagePath);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveClient(
            @RequestParam(value = "passport_front", required = false) MultipartFile passportFront,
            @RequestParam(value = "passport_back", required = false) MultipartFile passportBack,
            @RequestParam(value = "passport_face", required = false) MultipartFile passportFace,
            @RequestParam(value = "data_client", required = false) String clientData
    ) {
        try {
            List<MultipartFile> images = List.of(passportFront, passportBack, passportFace);
            imageService.validateImageFormat(images);
            Person person = personImageLinkService.save(passportFront, passportBack, passportFace, clientData);
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit-image/{id}")
    public ResponseEntity<?> editImage(@PathVariable @Min(1) Long id,
                                       @RequestParam(value = "faceImage", required = false) MultipartFile passportFace,
                                       @RequestParam(value = "backImage", required = false) MultipartFile passportBack,
                                       @RequestParam(value = "frontImage", required = false) MultipartFile passportFront){
        imageService.updateImages(id, passportFace, passportBack, passportFront);
        Person updatedPerson = clientService.findClientById(id);

        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Person> updateUser(@PathVariable @Min(1) Long id,
                                             @Valid @RequestBody PersonRequest personRequest) {

        Person person = clientService.editClient(id, personRequest);
        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable @Min(1) Long id) {
        clientService.deleteById(id);
        return new ResponseEntity<>("Пользователь был удален по идентификатору " + id + " успешно.", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Person>> searchUsers(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<Person> usersList = clientService.searchUsers(Status.valueOf(status), startDate, endDate);
        return ResponseEntity.ok(usersList);
    }

    @PostMapping("/export")
    public ResponseEntity<Resource> exportUsersToExcel(@RequestBody List<PersonRequest> clientList) {

        ByteArrayInputStream excelFile = excelExportService.exportClientsToExcel(clientList);

        InputStreamResource resource = new InputStreamResource(excelFile);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=clients.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<String>> getStatuses() {
        List<String> statuses = Arrays.stream(Status.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(statuses);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getRoles() {
        List<String> roles = Arrays.stream(Role.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }
}

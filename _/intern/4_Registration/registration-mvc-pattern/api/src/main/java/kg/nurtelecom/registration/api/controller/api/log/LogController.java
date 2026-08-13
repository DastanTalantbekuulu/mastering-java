package kg.nurtelecom.registration.api.controller.api.log;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.List;
import kg.nurtelecom.registration.api.service.log.LogQueryService;
import kg.nurtelecom.registration.common.payload.request.log.SimplePageRequest;
import kg.nurtelecom.registration.common.payload.response.log.EntityLogResponse;
import kg.nurtelecom.registration.common.payload.response.log.LogResponse;
import org.springframework.data.web.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/log")
public class LogController {
    private final LogQueryService service;

    public LogController(LogQueryService service) {
        this.service = service;
    }

    @GetMapping("/page")
    public ResponseEntity<PagedModel<LogResponse>> getPage(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "5", required = false) @Min(5) @Max(20) Integer size
    ) {
        return ResponseEntity.ok().body(service.findAll(page, size));
    }

    @PostMapping("/page")
    public ResponseEntity<PagedModel<LogResponse>> getPage(
            @Valid @RequestBody(required = false) SimplePageRequest request
    ) {
        return ResponseEntity.ok().body(service.findAll( request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogResponse> getLogById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findById(id));
    }

    @GetMapping("/record/{record}")
    public ResponseEntity<String> getLogCountByEntity(
            @PathVariable("record")
            @Size(min = 3, max = 255)
            String record
    ) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findCountLogByEntity(record));
    }

    @GetMapping("/record/{record}/{id}")
    public ResponseEntity<List<EntityLogResponse>> getAllLogByEntity(
            @PathVariable("record")
            @Size(min = 3, max = 255)
            String record,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(service.findAllByEntityAndEntityId(record, id));
    }

    @GetMapping("/{id}/record/state")
    public ResponseEntity<Object> getStateByLogId(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.findChangesById(id));
    }
}

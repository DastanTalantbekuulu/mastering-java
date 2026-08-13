package kg.nurtelecom.registration.api.controller.api;

import kg.nurtelecom.registration.api.service.WaitingListService;
import kg.nurtelecom.registration.common.payload.response.WaitingListResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Secured({"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_REGISTRAR"})
public class WaitingListController {

    private final WaitingListService waitingListService;

    public WaitingListController(WaitingListService waitingListService) {
        this.waitingListService = waitingListService;
    }

    @GetMapping("/waiting-list/client")
    public ResponseEntity<List<WaitingListResponse>> getClientWaitingList() {
        List<WaitingListResponse> waitingList = waitingListService.getWaitingList(true);
        return new ResponseEntity<>(waitingList, HttpStatus.OK);
    }

    @GetMapping("/waiting-list/staff")
    public ResponseEntity<List<WaitingListResponse>> getStaffWaitingList() {
        List<WaitingListResponse> waitingList = waitingListService.getWaitingList(false);
        return new ResponseEntity<>(waitingList, HttpStatus.OK);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<String> approveWaitingList(@PathVariable("id") Long id) {
        waitingListService.approvePerson(id);
        return new ResponseEntity<>("Approved", HttpStatus.OK);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<String> rejectWaitingList(@PathVariable("id") Long id) {
        waitingListService.rejectPerson(id);
        return new ResponseEntity<>("Rejected", HttpStatus.OK);
    }
}

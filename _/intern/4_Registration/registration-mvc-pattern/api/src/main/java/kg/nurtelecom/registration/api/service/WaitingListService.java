package kg.nurtelecom.registration.api.service;

import kg.nurtelecom.registration.common.payload.response.WaitingListResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WaitingListService {

    List<WaitingListResponse> getWaitingList(boolean isClient);
    void approvePerson(Long personId);
    void rejectPerson(Long personId);
}

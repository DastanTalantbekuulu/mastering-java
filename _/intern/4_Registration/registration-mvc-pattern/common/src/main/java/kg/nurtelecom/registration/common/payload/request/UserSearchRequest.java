package kg.nurtelecom.registration.common.payload.request;

import java.time.LocalDateTime;

public record UserSearchRequest(
        LocalDateTime fromDate,
        LocalDateTime toDate,
        Integer statusId
) {
}

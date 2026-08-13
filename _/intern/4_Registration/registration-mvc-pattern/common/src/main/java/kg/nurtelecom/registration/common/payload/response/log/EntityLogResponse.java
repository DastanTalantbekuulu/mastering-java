package kg.nurtelecom.registration.common.payload.response.log;

import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.registration.common.enums.Action;

public record EntityLogResponse(
        @JsonProperty("identifier")
        Long id,

        @JsonProperty("datetime")
        String logTime,

        @JsonProperty("user")
        String username,

        @JsonProperty("event")
        Action action,

        @JsonProperty("record_name")
        String entity,

        @JsonProperty("record_identifier")
        Long dataId,

        @JsonProperty("record_version")
        Integer version
) {
}

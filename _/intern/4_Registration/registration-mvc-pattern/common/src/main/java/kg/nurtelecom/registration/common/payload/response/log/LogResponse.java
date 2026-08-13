package kg.nurtelecom.registration.common.payload.response.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import kg.nurtelecom.registration.common.enums.Action;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record LogResponse(
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
        Integer version,

        @JsonProperty("ip")
        String idAddress,

        @JsonProperty("agent")
        String userAgent,

        @JsonProperty("code")
        Integer code
) {
}

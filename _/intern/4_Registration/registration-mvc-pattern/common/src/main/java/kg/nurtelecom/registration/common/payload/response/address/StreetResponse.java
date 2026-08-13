package kg.nurtelecom.registration.common.payload.response.address;

public record StreetResponse(
        Integer id,
        String name,
        Integer cityId,
        String city
) {
}

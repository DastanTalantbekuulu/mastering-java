package kg.nurtelecom.registration.common.payload.response.address;

public record DistrictResponse(
        Integer id,
        String name,
        Integer regionId,
        String region
) {
}

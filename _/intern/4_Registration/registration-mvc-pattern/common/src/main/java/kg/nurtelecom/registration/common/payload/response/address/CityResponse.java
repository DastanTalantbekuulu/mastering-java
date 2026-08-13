package kg.nurtelecom.registration.common.payload.response.address;

public record CityResponse(
        Integer id,
        String name,
        Integer cityTypeId,
        String cityType,
        Integer regionId,
        String region,
        Integer districtId,
        String district
) {
}

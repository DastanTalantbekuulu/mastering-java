package kg.nurtelecom.registration.common.payload.request.address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CityRequest(
        @NotBlank(message = "Название города не может быть пустым")
        @Size(min = 2, max = 100, message = "Длина строки от 2 до 100 символов")
        @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+((?:[ -.|'])[a-zA-Zа-яА-ЯёЁ]+)*$", message = "Поле должно содержать только буквы латиницы или кириллицы")
        String name,

        @NotNull(message = "ID типа населенного пункта обязателен")
        @Min(value = 1, message = "ID типа населенного пункта должен быть больше 0")
        Integer cityTypeId,

        @Min(value = 1, message = "ID области должен быть больше 0")
        Integer regionId,

        @Min(value = 1, message = "ID района должен быть больше 0")
        Integer districtId
) {
}

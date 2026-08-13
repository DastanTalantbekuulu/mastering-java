package kg.nurtelecom.registration.common.payload.request.address;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DistrictRequest(
        @NotBlank(message = "Название района не может быть пустым")
        @Size(min = 2, max = 100, message = "Длина строки от 2 до 100 символов")
        @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+((?:[ -.|'])[a-zA-Zа-яА-ЯёЁ]+)*$", message = "Поле должно содержать только буквы латиницы или кириллицы")
        String name,

        @NotNull(message = "ID области обязателен")
        @Min(value = 1, message = "ID области должен быть больше 0")
        Integer regionId
) {
}

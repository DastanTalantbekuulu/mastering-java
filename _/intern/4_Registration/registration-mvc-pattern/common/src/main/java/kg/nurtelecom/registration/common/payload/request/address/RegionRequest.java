package kg.nurtelecom.registration.common.payload.request.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegionRequest(
        @NotBlank(message = "Название области не может быть пустым")
        @Size(min = 2, max = 100, message = "Длина строки от 2 до 100 символов")
        @Pattern(regexp = "^[a-zA-Zа-яА-ЯёЁ]+((?:[ -.|'])[a-zA-Zа-яА-ЯёЁ]+)*$", message = "Поле должно содержать только буквы латиницы или кириллицы")
        String name
) {
}

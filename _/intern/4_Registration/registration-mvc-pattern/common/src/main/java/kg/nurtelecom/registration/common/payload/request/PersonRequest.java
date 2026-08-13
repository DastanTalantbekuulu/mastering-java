package kg.nurtelecom.registration.common.payload.request;

import jakarta.validation.constraints.*;
import kg.nurtelecom.registration.common.enums.Role;
import kg.nurtelecom.registration.common.enums.Status;

import java.time.LocalDate;

public record PersonRequest(
        @NotBlank(message = "Поле не может быть пустым.")
        @Size(min = 2, max = 24, message = "Минимум 2 символа, максимум 24.")
        String firstName,

        @NotBlank(message = "Поле не может быть пустым.")
        @Size(min = 2, max = 24, message = "Минимум 2 символа, максимум 24.")
        String lastName,

        @NotBlank(message = "Поле не может быть пустым.")
        @Size(min = 2, max = 24, message = "Минимум 2 символа, максимум 24.")
        String middleName,

        @NotNull(message = "Пол обязателен")
        String personGender,

        @NotBlank(message = "Гражданство обязательно")
        String nationality,

        @NotNull(message = "Дата рождения обязательна.")
        @Past(message = "Дата рождения должна быть в прошлом.")
        LocalDate dateOfBirth,

        @NotBlank(message = "Требуется ИНН.")
        @Pattern(regexp = "\\d{14,15}", message = "ИНН должен состоять из 14-15 цифр.")
        String identificationNumber,

        @NotNull(message = "Дата выдачи паспорта обязательна.")
        @PastOrPresent(message = "Дата выдачи паспорта не может быть в будущем.")
        LocalDate dateOfIssue,

        @NotNull(message = "Дата окончания паспорта обязательна.")
        @Future(message = "Дата окончания паспорта должна быть в будущем.")
        LocalDate dateOfExpiry,

        @NotBlank(message = "Поле не может быть пустым.")
        @Email(message = "Введите корректный адрес электронной почты.")
        String email,

        Role role,
        Status status,
        String documentId,
        String issuingAuthority,

        @Min(value = 1, message = "Регион должен быть положительным числом.")
        Integer regionId,

        @Min(value = 1, message = "Район должен быть положительным числом.")
        Integer districtId,

        @Min(value = 1, message = "Город должен быть положительным числом.")
        Integer cityId,

        @Min(value = 1, message = "Улица должна быть положительным числом.")
        Integer streetId,

        @Min(value = 1, message = "Номер дома должен быть положительным числом.")
        Integer house,

        @Min(value = 1, message = "Номер квартиры должен быть положительным числом.")
        Integer apartment
) {
        @AssertTrue(message = "Паспорт можно получить только с 16 лет.")
        public boolean isDateOfIssueValid() {
                return dateOfBirth == null || dateOfIssue == null || !dateOfIssue.isBefore(dateOfBirth.plusYears(16));
        }

        @AssertTrue(message = "Дата окончания паспорта должна быть позже даты выдачи.")
        public boolean isExpiryDateValid() {
                return dateOfIssue == null || dateOfExpiry == null || dateOfExpiry.isAfter(dateOfIssue);
        }

        @AssertTrue(message = "Дата окончания паспорта должна быть ровно на 10 лет больше даты выдачи.")
        public boolean isExpiryDateExactlyTenYears() {
                return dateOfIssue == null || dateOfExpiry == null || dateOfExpiry.equals(dateOfIssue.plusYears(10));
        }

        @AssertTrue(message = "Дата выдачи паспорта не должна быть в будущем.")
        public boolean isDateOfIssueNotInFuture() {
                return dateOfIssue == null || !dateOfIssue.isAfter(LocalDate.now());
        }

        @AssertTrue(message = "Дата выдачи и дата окончания паспорта не могут совпадать.")
        public boolean isIssueAndExpiryDifferent() {
                return dateOfIssue == null || dateOfExpiry == null || !dateOfIssue.equals(dateOfExpiry);
        }
}


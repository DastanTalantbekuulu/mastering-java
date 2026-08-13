package kg.nurtelecom.registration.common.payload.request;

import kg.nurtelecom.registration.common.enums.AgreementStatus;

public record UserAgreementRequest(String content, AgreementStatus status) {}
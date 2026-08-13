package kg.nurtelecom.registration.api.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorsResponse {
    private int code;
    private String message;
    private Map<String, List<String>> validationErrors;

    public ValidationErrorsResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.validationErrors = new HashMap<>();
    }

    public void put(String field, String error) {
        validationErrors.computeIfAbsent(field, s -> new ArrayList<>()).add(error);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setValidationErrors(Map<String, List<String>> validationErrors) {
        this.validationErrors = validationErrors;
    }
}


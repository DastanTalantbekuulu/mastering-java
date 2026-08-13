package kg.nurtelecom.registration.api.log.service;

import kg.nurtelecom.registration.common.enums.Action;

public interface SecurityLoggingService {

    long save(Action action, String username, String ipAddress, String userAgent, int responseCode);
}


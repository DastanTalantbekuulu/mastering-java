package kg.nurtelecom.registration.api.service.export;

import kg.nurtelecom.registration.common.payload.request.PersonRequest;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExcelExportService {
    ByteArrayInputStream exportClientsToExcel(List<PersonRequest> clients);
}

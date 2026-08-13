package kg.nurtelecom.registration.api.service.log;

import java.util.List;
import kg.nurtelecom.registration.common.payload.request.log.SimplePageRequest;
import kg.nurtelecom.registration.common.payload.response.log.EntityLogResponse;
import kg.nurtelecom.registration.common.payload.response.log.LogResponse;
import org.springframework.data.web.PagedModel;

public interface LogQueryService {

    PagedModel<LogResponse> findAll( SimplePageRequest request);

    PagedModel<LogResponse> findAll();

    PagedModel<LogResponse> findAll(int page, int size);

    List<EntityLogResponse> findAllByEntityAndEntityId(String entity, Long entityId);

    LogResponse findById(Long id);

    Object findChangesById(Long id);

    String findCountLogByEntity(String entity);
}

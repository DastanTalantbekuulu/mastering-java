package kg.nurtelecom.registration.api.log.service;

import jakarta.persistence.Entity;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import kg.nurtelecom.registration.common.enums.Action;

public interface VersionService {

    Map<Class<?>, Long> classMap = new ConcurrentHashMap<>();

    default boolean versioning(Class<?> clazz, Action action, String username, long dataId, String changes) {
        return versioning(clazz, action, username, Long.valueOf(dataId), changes);
    }

    default boolean versioning(Class<?> clazz, Action action, String username, Long dataId, String changes) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            long entityId = findIdByClass(clazz);
            long versionId = save(action, username, entityId, dataId, changes);
            return versionId > 0;
        }
        return false;
    }

    long findIdByClass(Class<?> clazz);

    default long save(Action action, String username, long entityId, long dataId, String changes) {
        return save(action, username, entityId, Long.valueOf(dataId), changes);
    }

    long save(Action action, String username, long entityId, Long dataId, String changes);

}

package kg.nurtelecom.registration.common.entity.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import kg.nurtelecom.registration.common.enums.Action;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "log")
public class Log {
    @JsonProperty("identifier")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("datetime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "log_time")
    private LocalDateTime logTime;

    @JsonProperty("user")
    @Column(name = "username")
    private String username;

    @JsonProperty("event")
    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    private Action action;

    @JsonIgnore
    @Column(name = "table_name")
    private String tableName;

    @JsonIgnore
    @Column(name = "clazz")
    private Class<?> clazz;

    @JsonProperty("record_name")
    @Column(name = "entity")
    private String entity;

    @JsonProperty("record_identifier")
    @Column(name = "entity_id")
    private Long entityId;

    @JsonProperty("record_version")
    @Column(name = "version")
    private Integer version;

    @JsonProperty("record_state")
    @Column(name = "changes")
    @JdbcTypeCode(SqlTypes.JSON)
    private String changes;

    public Log() {
    }

    public Log(Long id, LocalDateTime logTime, String username, Action action, String tableName, Class<?> clazz, String entity, Long entityId, Integer version, String changes) {
        this.id = id;
        this.logTime = logTime;
        this.username = username;
        this.action = action;
        this.tableName = tableName;
        this.clazz = clazz;
        this.entity = entity;
        this.entityId = entityId;
        this.version = version;
        this.changes = changes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getChanges() {
        return changes;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }
}

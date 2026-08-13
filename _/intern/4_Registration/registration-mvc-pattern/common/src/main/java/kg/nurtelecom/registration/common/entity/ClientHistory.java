package kg.nurtelecom.registration.common.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "client_history")

public class ClientHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "operation_type")
    private String operationType; // "CREATE", "UPDATE", "DELETE"

    @Column(name = "changed_by")
    private String changedBy;

    @Column(name = "change_timestamp")
    private LocalDateTime changeTimestamp;

    @Column(name = "changes")
    private String changes;

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public String getOperationType() {
        return operationType;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public LocalDateTime getChangeTimestamp() {
        return changeTimestamp;
    }

    public String getChanges() {
        return changes;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public void setChangeTimestamp(LocalDateTime changeTimestamp) {
        this.changeTimestamp = changeTimestamp;
    }

    public void setChanges(String changes) {
        this.changes = changes;
    }
}

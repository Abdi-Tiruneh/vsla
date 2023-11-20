package vsla.organization.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import vsla.organization.organization.Organization;
import vsla.utils.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@SQLDelete(sql = "UPDATE projects SET deleted = true WHERE project_id=?")
@Where(clause = "deleted=false")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}

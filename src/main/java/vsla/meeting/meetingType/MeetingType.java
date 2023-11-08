package vsla.meeting.meetingType;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_types")
@Data
public class MeetingType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_type_id")
    private Long meetingTypeId;

    @Column(name = "meeting_type_name", nullable = false, unique = true)
    private String meetingTypeName;

    @Column(name = "is_active")
    private boolean isActive = Boolean.TRUE;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

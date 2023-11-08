package vsla.meeting.meetingInterval;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "meeting_intervals")
@Data
public class MeetingInterval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_interval_id")
    private Long meetingIntervalId;

    @Column(name = "meeting_interval_name", nullable = false)
    private String meetingIntervalName;

    @Column(name = "interval_in_days")
    private int intervalInDays;

    @Column(name = "is_active")
    private boolean isActive = Boolean.TRUE;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

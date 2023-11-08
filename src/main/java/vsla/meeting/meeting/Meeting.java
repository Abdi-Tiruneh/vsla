package vsla.meeting.meeting;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vsla.group.Group;
import vsla.meeting.meetingInterval.MeetingInterval;
import vsla.meeting.meetingType.MeetingType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
@Data
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long meetingId;

    @Column(name = "current_round")
    private int currentRound;

    @Column(name = "meeting_date")
    private LocalDate nextMeetingDate;

    @ManyToOne
    @JoinColumn(name = "meeting_interval_id", nullable = false)
    private MeetingInterval meetingInterval;

    @ManyToOne
    @JoinColumn(name = "meeting_type_id", nullable = false)
    private MeetingType meetingType;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

package vsla.meeting.meetingInterval;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingIntervalRepository extends JpaRepository<MeetingInterval, Long> {
}
package vsla.meeting.meetingType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingTypeRepository extends JpaRepository<MeetingType, Long> {

    Optional<MeetingType> findByMeetingTypeNameIgnoreCase(String typeName);
}
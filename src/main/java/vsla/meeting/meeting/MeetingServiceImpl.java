package vsla.meeting.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;

    @Override
    public Meeting EditMeeting(Long meetingId, Meeting meeting) {
       LocalDateTime localDateTime=  LocalDateTime.now();
       Meeting meetingToBeUpdated= meetingRepository.findMeetingByMeetingId(meetingId);
       meetingToBeUpdated.setCurrentRound(meeting.getCurrentRound());
       meetingToBeUpdated.setIsEnabled(meeting.getIsEnabled());
       meetingToBeUpdated.setGroup(meeting.getGroup());
       meetingToBeUpdated.setMeetingInterval(meeting.getMeetingInterval());
       meetingToBeUpdated.setMeetingReason(meeting.getMeetingReason());
       meetingToBeUpdated.setMeetingType(meeting.getMeetingType());
       meetingToBeUpdated.setIntervalDays(meeting.getIntervalDays());
       meetingToBeUpdated.setNextMeetingDate(meeting.getNextMeetingDate());
       meetingToBeUpdated.setCreatedAt(meetingToBeUpdated.getCreatedAt());
       meetingToBeUpdated.setUpdatedAt(localDateTime);
       return meetingRepository.save(meetingToBeUpdated);
    }

    @Override
    public Meeting CancleMeeting(Long meetingId) {
        Meeting meeting= meetingRepository.findMeetingByMeetingId(meetingId);
        meeting.setIsEnabled(false);
        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        LocalDateTime localDateTime= LocalDateTime.now();
        meeting.setCreatedAt(localDateTime);
        meeting.setUpdatedAt(localDateTime);
        meeting.setIsEnabled(true);
        return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getAllMeetingsByGroup(Long groupId) {
       return meetingRepository.findByGroupGroupId(groupId);
    }

    @Override
    public Meeting getMeetingById(Long meetingId) {
        return meetingRepository.findMeetingByMeetingId(meetingId);
    }

    @Override
    public Meeting ContinueMeeting(Long meetingId, int nextRound) {
        Meeting meeting= meetingRepository.findMeetingByMeetingId(meetingId);
        //  LocalDateTime updatedDate = currentDate.plus(3, ChronoUnit.DAYS);
        LocalDateTime updatedDate=meeting.getNextMeetingDate().plus(meeting.getIntervalDays(), ChronoUnit.DAYS);
        meeting.setNextMeetingDate(updatedDate);
        meeting.setIsEnabled(true);
        meeting.setCurrentRound(nextRound);
       return meetingRepository.save(meeting);
    }

    @Override
    public List<Meeting> getActiveMeetingsByGroup(Long groupId) {
        return meetingRepository.findMeetingByGroupGroupIdAndIsEnabled(groupId, true);
    }

    @Override
    public List<Meeting> getInActiveMeetingsByGroup(Long groupId) {
        return meetingRepository.findMeetingByGroupGroupIdAndIsEnabled(groupId, false);
    }
    

   
}

package vsla.meeting.meeting;

import java.util.List;

public interface MeetingService {
    Meeting ContinueMeeting(Long meetingId, int nextRound);

    Meeting EditMeeting(Long meetingId,Meeting meeting);

    Meeting CancleMeeting(Long meetingId);

    Meeting createMeeting(Meeting meeting);

    List<Meeting> getAllMeetingsByGroup(Long groupId);

    List<Meeting> getActiveMeetingsByGroup(Long groupId);

    List<Meeting> getInActiveMeetingsByGroup(Long groupId);

     Meeting getMeetingById(Long meetingId);

}

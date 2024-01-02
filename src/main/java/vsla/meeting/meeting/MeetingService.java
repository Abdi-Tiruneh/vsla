package vsla.meeting.meeting;

import java.util.List;

import vsla.meeting.meeting.dto.MeetingDTO;

public interface MeetingService {
    Meeting ContinueMeeting(Long meetingId, int nextRound);

    Meeting EditMeeting(Long meetingId,Meeting meeting);

    Meeting CancleMeeting(Long meetingId);

    Meeting createMeeting(Meeting meeting);

    List<MeetingDTO> getAllMeetingsByGroup(Long groupId);

    List<MeetingDTO> getActiveMeetingsByGroup(Long groupId);

   List<MeetingDTO> getInActiveMeetingsByGroup(Long groupId);

   MeetingDTO getMeetingById(Long meetingId);

}

package vsla.meeting.meeting;

import vsla.group.Group;
import vsla.meeting.meeting.dto.MeetingReq;
import vsla.meeting.meeting.dto.MeetingResponse;

import java.time.LocalDate;
import java.util.List;

public interface MeetingService {
    MeetingResponse createMeeting(MeetingReq meetingReq);

    void createDefaultMeeting(Group group, Long meetingIntervalId, LocalDate meetingDate);

    MeetingResponse updateMeetingRound(Long meetingId);

    List<MeetingResponse> getAllMeetingsByGroup(Long groupId);

    Meeting getMeetingById(Long meetingId);
}

package vsla.meeting.meetingInterval;

import vsla.meeting.meetingInterval.dto.MeetingIntervalReq;

import java.util.List;

public interface MeetingIntervalService {
    MeetingInterval createMeetingInterval(MeetingIntervalReq meetingIntervalReq);

    MeetingInterval updateMeetingInterval(Long meetingId, MeetingIntervalReq meetingIntervalReq);

    List<MeetingInterval> getAllMeetingIntervals();

    MeetingInterval getMeetingIntervalById(Long id);
}

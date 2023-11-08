package vsla.meeting.meeting.dto;

import vsla.meeting.meeting.Meeting;

public class MeetingMapper {
    public static MeetingResponse toResponse(Meeting meeting) {
        String meetingInterval = meeting.getMeetingInterval().getIntervalInDays() + " days";

        return MeetingResponse.builder()
                .meetingId(meeting.getMeetingId())
                .meetingInterval(meetingInterval)
                .currentRound(meeting.getCurrentRound())
                .meetingType(meeting.getMeetingType().getMeetingTypeName())
                .nextMeetingDate(meeting.getNextMeetingDate())
                .createdAt(meeting.getCreatedAt())
                .updatedAt(meeting.getUpdatedAt())
                .build();
    }
}


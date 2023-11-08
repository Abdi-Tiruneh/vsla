package vsla.meeting.meetingType;

import vsla.meeting.meetingType.dto.MeetingTypeReq;

import java.util.List;

public interface MeetingTypeService {
    MeetingType createMeetingType(MeetingTypeReq meetingTypeReq);

    MeetingType updateMeetingType(Long meetingTypeId, MeetingTypeReq meetingTypeReq);

    List<MeetingType> getAllMeetingTypes();

    MeetingType getMeetingTypeById(Long meetingTypeId);

    MeetingType getByMeetingTypeName(String typeName);
}

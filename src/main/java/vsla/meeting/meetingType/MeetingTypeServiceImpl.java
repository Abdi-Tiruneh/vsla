package vsla.meeting.meetingType;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsla.exceptions.customExceptions.ResourceNotFoundException;
import vsla.meeting.meetingType.dto.MeetingTypeReq;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingTypeServiceImpl implements MeetingTypeService {
    private final MeetingTypeRepository meetingTypeRepository;

    @Override
    public MeetingType createMeetingType(MeetingTypeReq meetingTypeReq) {
        MeetingType meetingType = new MeetingType();
        meetingType.setMeetingTypeName(meetingTypeReq.getMeetingTypeName());

        return meetingTypeRepository.save(meetingType);
    }


    @Override
    public MeetingType updateMeetingType(Long meetingTypeId, MeetingTypeReq meetingTypeReq) {
        MeetingType meetingType = getMeetingTypeById(meetingTypeId);

        if (meetingTypeReq.getMeetingTypeName() != null)
            meetingType.setMeetingTypeName(meetingTypeReq.getMeetingTypeName());

        if (meetingTypeReq.getIsActive() != null)
            meetingType.setActive(meetingTypeReq.getIsActive());

        return meetingTypeRepository.save(meetingType);
    }

    @Override
    public List<MeetingType> getAllMeetingTypes() {
        return meetingTypeRepository.findAll();
    }

    @Override
    public MeetingType getMeetingTypeById(Long meetingTypeId) {
        return meetingTypeRepository.findById(meetingTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting Type not found"));
    }

    @Override
    public MeetingType getByMeetingTypeName(String typeName) {
        return meetingTypeRepository.findByMeetingTypeNameIgnoreCase(typeName)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting Type not found"));
    }
}

package vsla.meeting.meeting;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsla.exceptions.customExceptions.ResourceAlreadyExistsException;
import vsla.exceptions.customExceptions.ResourceNotFoundException;
import vsla.group.Group;
import vsla.group.GroupRepository;
import vsla.meeting.meeting.dto.MeetingMapper;
import vsla.meeting.meeting.dto.MeetingReq;
import vsla.meeting.meeting.dto.MeetingResponse;
import vsla.meeting.meetingInterval.MeetingInterval;
import vsla.meeting.meetingInterval.MeetingIntervalService;
import vsla.meeting.meetingType.MeetingType;
import vsla.meeting.meetingType.MeetingTypeService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingServiceImpl implements MeetingService {
    private final MeetingRepository meetingRepository;
    private final MeetingTypeService meetingTypeService;
    private final MeetingIntervalService meetingIntervalService;
    private final GroupRepository groupRepository;

    @Override
    public MeetingResponse createMeeting(MeetingReq meetingReq) {
        // Fetch necessary entities
        Group group = getGroupById(meetingReq.getGroupId());
        MeetingType meetingType = meetingTypeService.getMeetingTypeById(meetingReq.getMeetingTypeId());
        MeetingInterval meetingInterval = meetingIntervalService.getMeetingIntervalById(meetingReq.getMeetingIntervalId());

        // Check if a meeting of the same type already exists for the group
        List<Meeting> meetings = meetingRepository.findByGroupGroupId(meetingReq.getGroupId());
        for (Meeting meeting : meetings) {
            if (meeting.getMeetingType().equals(meetingType))
                throw new ResourceAlreadyExistsException("A meeting of type '" + meetingType.getMeetingTypeName() + "' already exists for your group.");
        }

        // Create a new meeting
        Meeting newMeeting = new Meeting();
        newMeeting.setMeetingInterval(meetingInterval);
        newMeeting.setMeetingType(meetingType);
        newMeeting.setGroup(group);
        newMeeting.setNextMeetingDate(meetingReq.getMeetingDate());

        newMeeting = meetingRepository.save(newMeeting);

        return MeetingMapper.toResponse(newMeeting);
    }

    @Override
    public void createDefaultMeeting(Group group, Long meetingIntervalId, LocalDate meetingDate) {
        // Fetch necessary entities
        MeetingType meetingType = meetingTypeService.getByMeetingTypeName("Round Payment");
        MeetingInterval meetingInterval = meetingIntervalService.getMeetingIntervalById(meetingIntervalId);

        // Create a new meeting
        Meeting newMeeting = new Meeting();
        newMeeting.setMeetingInterval(meetingInterval);
        newMeeting.setMeetingType(meetingType);
        newMeeting.setGroup(group);
        newMeeting.setNextMeetingDate(meetingDate);

        meetingRepository.save(newMeeting);
    }

    @Override
    public MeetingResponse updateMeetingRound(Long meetingId) {
        Meeting meeting = getMeetingById(meetingId);

        // Calculate the updated round and next meeting date
        int updatedRound = meeting.getCurrentRound() + 1;
        LocalDate nextMeetingDate = LocalDate.now().plusDays(meeting.getMeetingInterval().getIntervalInDays());

        // Update the meeting details
        meeting.setCurrentRound(updatedRound);
        meeting.setNextMeetingDate(nextMeetingDate);

        meeting = meetingRepository.save(meeting);

        return MeetingMapper.toResponse(meeting);
    }

    @Override
    public List<MeetingResponse> getAllMeetingsByGroup(Long groupId) {
        List<Meeting> meetings = meetingRepository.findByGroupGroupId(groupId);
        return meetings.stream()
                .map(MeetingMapper::toResponse)
                .toList();
    }

    @Override
    public Meeting getMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting with ID " + meetingId + " not found."));
    }

    private Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
    }
}

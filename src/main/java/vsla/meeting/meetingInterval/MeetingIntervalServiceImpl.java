package vsla.meeting.meetingInterval;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vsla.exceptions.customExceptions.ResourceNotFoundException;
import vsla.meeting.meetingInterval.dto.MeetingIntervalReq;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingIntervalServiceImpl implements MeetingIntervalService {
    private final MeetingIntervalRepository meetingIntervalRepository;

    @Override
    public MeetingInterval createMeetingInterval(MeetingIntervalReq meetingIntervalReq) {
        MeetingInterval meetingInterval = new MeetingInterval();
        meetingInterval.setMeetingIntervalName(meetingIntervalReq.getMeetingIntervalName());
        meetingInterval.setIntervalInDays(meetingIntervalReq.getIntervalInDays());

        return meetingIntervalRepository.save(meetingInterval);
    }


    @Override
    public MeetingInterval updateMeetingInterval(Long id, MeetingIntervalReq meetingIntervalReq) {
        MeetingInterval meetingInterval = getMeetingIntervalById(id);

        if (meetingIntervalReq.getMeetingIntervalName() != null)
            meetingInterval.setMeetingIntervalName(meetingIntervalReq.getMeetingIntervalName());

        if (meetingIntervalReq.getIsActive() != null)
            meetingInterval.setActive(meetingIntervalReq.getIsActive());

        if (meetingIntervalReq.getIntervalInDays() != null)
            meetingInterval.setIntervalInDays(meetingIntervalReq.getIntervalInDays());

        return meetingIntervalRepository.save(meetingInterval);
    }

    @Override
    public List<MeetingInterval> getAllMeetingIntervals() {
        return meetingIntervalRepository.findAll();
    }

    @Override
    public MeetingInterval getMeetingIntervalById(Long id) {
        return meetingIntervalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meeting Interval not found"));
    }
}

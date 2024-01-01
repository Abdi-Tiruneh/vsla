package vsla.meeting.meeting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/meetings")
public class MeetingController {
    @Autowired
    private final MeetingService meetingService;
    @GetMapping("/getAllMeetings/{groupId}")
    List<Meeting> getAllMeetings(@PathVariable Long groupId) {
        return meetingService.getAllMeetingsByGroup(groupId);
    }

    @GetMapping("/getActiveMeetings/{groupId}")
    List<Meeting> getActiveMeetings(@PathVariable Long groupId) {
        return meetingService.getActiveMeetingsByGroup(groupId);
    }

     @GetMapping("/getInActiveMeetings/{groupId}")
    List<Meeting> getInActiveMeetings(@PathVariable Long groupId) {
        return meetingService.getInActiveMeetingsByGroup(groupId);
    }

    @GetMapping("/getMeetingById/{meetingId}")
    Meeting getMeetingById(@PathVariable Long meetingId) {
        return meetingService.getMeetingById(meetingId);
    }

    @PostMapping("/createMeeting")
    Meeting createMeating(@RequestBody Meeting meeting) {
        return meetingService.createMeeting(meeting);
    }

    @PutMapping("/editMeeting/{meetingId}")
    Meeting editMeeeting(@PathVariable Long meetingId, @RequestBody	Meeting meeting) {
        return meetingService.EditMeeting(null, meeting);
    }

    @PutMapping("/cancelMeeting/{meetingId}")
    Meeting cancelMeeeting(@PathVariable Long meetingId) {
        return meetingService.CancleMeeting(meetingId);
    }

    @PutMapping("/continueMeeting/{meetingId}/{nextRound}")
    Meeting continueMeeting(@PathVariable Long meetingId,@PathVariable Integer nextRound) {
        return meetingService.ContinueMeeting(meetingId, nextRound);
    }



}



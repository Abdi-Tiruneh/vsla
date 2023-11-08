package vsla.meeting.meeting;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.meeting.meeting.dto.MeetingReq;
import vsla.meeting.meeting.dto.MeetingResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meetings")
@Tag(name = "Meetings API.")
public class MeetingController {
    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MeetingResponse>> getAllMeetingsByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(meetingService.getAllMeetingsByGroup(groupId));
    }

    @PostMapping
    public ResponseEntity<MeetingResponse> createMeeting(@RequestBody @Valid MeetingReq meetingReq) {
        MeetingResponse meetingResponse = meetingService.createMeeting(meetingReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingResponse);
    }

    @PutMapping("/{meetingId}/meeting-round")
    public ResponseEntity<MeetingResponse> updateMeetingRound(@PathVariable Long meetingId) {
        return ResponseEntity.ok(meetingService.updateMeetingRound(meetingId));
    }
}



package vsla.meeting.meetingInterval;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.meeting.meetingInterval.dto.MeetingIntervalReq;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting-intervals")
@Tag(name = "Meeting Type API.")
public class MeetingIntervalController {
    private final MeetingIntervalService meetingIntervalService;

    public MeetingIntervalController(MeetingIntervalService meetingIntervalService) {
        this.meetingIntervalService = meetingIntervalService;
    }

    @GetMapping
    public ResponseEntity<List<MeetingInterval>> getAllMeetingIntervals() {
        return ResponseEntity.ok(meetingIntervalService.getAllMeetingIntervals());
    }

    @PostMapping
    public ResponseEntity<MeetingInterval> createMeetingInterval(@RequestBody @Valid MeetingIntervalReq meetingIntervalReq) {
        MeetingInterval meetingInterval = meetingIntervalService.createMeetingInterval(meetingIntervalReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingInterval);
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity<MeetingInterval> updateMeetingInterval(@PathVariable Long meetingId, @RequestBody MeetingIntervalReq meetingIntervalReq) {
        return ResponseEntity.ok(meetingIntervalService.updateMeetingInterval(meetingId, meetingIntervalReq));
    }
}



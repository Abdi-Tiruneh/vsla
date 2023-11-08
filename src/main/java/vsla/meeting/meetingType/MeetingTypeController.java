package vsla.meeting.meetingType;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.meeting.meetingType.dto.MeetingTypeReq;

import java.util.List;

@RestController
@RequestMapping("/api/v1/meeting-types")
@Tag(name = "Meeting Type API.")
public class MeetingTypeController {
    private final MeetingTypeService meetingTypeService;

    public MeetingTypeController(MeetingTypeService meetingTypeService) {
        this.meetingTypeService = meetingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<MeetingType>> getAllMeetingTypes() {
        return ResponseEntity.ok(meetingTypeService.getAllMeetingTypes());
    }

    @PostMapping
    public ResponseEntity<MeetingType> createMeetingType(@RequestBody @Valid MeetingTypeReq meetingTypeReq) {
        MeetingType meetingType = meetingTypeService.createMeetingType(meetingTypeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(meetingType);
    }

    @PutMapping("/{meetingTypeId}")
    public ResponseEntity<MeetingType> updateMeetingType(@PathVariable Long meetingTypeId, @RequestBody MeetingTypeReq meetingTypeReq) {
        return ResponseEntity.ok(meetingTypeService.updateMeetingType(meetingTypeId, meetingTypeReq));
    }
}



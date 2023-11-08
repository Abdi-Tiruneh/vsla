package vsla.meeting.meetingType.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MeetingTypeReq {

    @NotBlank(message = "Meeting Type Name Name is required")
    private String meetingTypeName;

    private Boolean isActive;
}
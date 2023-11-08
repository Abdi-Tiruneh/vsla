package vsla.meeting.meetingInterval.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MeetingIntervalReq {

    @NotBlank(message = "Meeting Interval Name is required")
    private String meetingIntervalName;

    @NotNull(message = "Meeting Interval day is required")
    private Integer intervalInDays;

    private Boolean isActive;
}
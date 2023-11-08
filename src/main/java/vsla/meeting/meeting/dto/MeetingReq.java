package vsla.meeting.meeting.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class MeetingReq {

    @NotNull(message = "Meeting Interval is required")
    private Long meetingIntervalId;

    @NotNull(message = "Meeting Type is required")
    private Long meetingTypeId;

    @NotNull(message = "Group is required")
    private Long groupId;

    @NotNull(message = "Opening Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;
}
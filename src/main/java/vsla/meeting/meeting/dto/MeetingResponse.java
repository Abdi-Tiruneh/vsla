package vsla.meeting.meeting.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingResponse {

    private Long meetingId;

    private String meetingInterval;

    private int currentRound;

    private String meetingType;
    private LocalDate nextMeetingDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

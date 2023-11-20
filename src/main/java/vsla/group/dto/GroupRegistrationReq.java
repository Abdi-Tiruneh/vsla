package vsla.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import vsla.userManager.address.dto.AddressRegistrationReq;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class GroupRegistrationReq {

    @NotBlank(message = "Group Name is required")
    private String groupName;

    @NotNull(message = "Group Size is required")
    private Integer groupSize;

    @NotNull(message = "Entry Fee Size is required")
    private BigDecimal entryFee;

    @NotNull(message = "Address is required")
    private AddressRegistrationReq address;

    @NotNull(message = "Opening Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate meetingDate;

    @NotNull(message = "projectId is required")
    private Long projectId;

    @NotNull(message = "groupTypeId is required")
    private Long groupTypeId;

    @NotNull(message = "Meeting Interval is required")
    private Long meetingIntervalId;
}
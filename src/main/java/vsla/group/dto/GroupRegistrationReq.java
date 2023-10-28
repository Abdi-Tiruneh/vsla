package vsla.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import vsla.userManager.address.dto.AddressRegistrationReq;

import java.math.BigDecimal;

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
}
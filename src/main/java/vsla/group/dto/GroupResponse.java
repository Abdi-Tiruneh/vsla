package vsla.group.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vsla.userManager.address.Address;
import vsla.userManager.user.dto.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupResponse {

    private Long groupId;

    private String groupName;

    private Integer groupSize;

    private BigDecimal entryFee;

    private UserResponse groupAdmin;

    private Address address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
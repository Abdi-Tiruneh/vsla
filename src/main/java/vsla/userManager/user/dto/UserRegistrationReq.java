package vsla.userManager.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import vsla.userManager.address.dto.AddressRegistrationReq;

@Data
public class UserRegistrationReq {

    @NotBlank(message = "Full name is required")
    @Size(min = 2, message = "Full name must be at least 2 characters")
    private String fullName;

    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "password is required")
    @Size(min = 4, max = 6, message = "password must be between 6 and 20 characters")
    private String password;

    private boolean proxyEnabled;

    @NotNull(message = "Address is required")
    private AddressRegistrationReq address;

    @NotNull(message = "Role is required")
    private Short roleId;

    @NotNull(message = "Company is required")
    private Short companyId;
}
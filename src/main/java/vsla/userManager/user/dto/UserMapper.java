package vsla.userManager.user.dto;

import vsla.userManager.user.Users;

public class UserMapper {
    public static UserResponse toUserResponse(Users user) {

        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .phoneNumber(user.getUsername())
                .role(user.getRole().getRoleName())
                .company(user.getCompany().getCompanyName())
                .address(user.getAddress())
                .userStatus(user.getUserStatus())
                .lastLoggedIn(user.getLastLoggedIn())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static UserResponse toMiniUserResponse(Users user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .role(user.getRole().getRoleName())
                .build();
    }
}


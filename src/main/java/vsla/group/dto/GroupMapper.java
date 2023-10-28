package vsla.group.dto;

import vsla.group.Group;
import vsla.userManager.user.dto.UserMapper;
import vsla.userManager.user.dto.UserResponse;
import vsla.userManager.user.Users;

public class GroupMapper {
    public static GroupResponse toGroupResponse(Group group) {

        Users user = group.getGroupAdmin();
        UserResponse userResponse = UserMapper.toMiniUserResponse(user);

        return GroupResponse.builder()
                .groupId(group.getGroupId())
                .groupName(group.getGroupName())
                .groupSize(group.getGroupSize())
                .entryFee(group.getEntryFee())
                .groupAdmin(userResponse)
                .address(group.getAddress())
                .createdAt(group.getCreatedAt())
                .updatedAt(group.getUpdatedAt())
                .build();

    }
}


package vsla.group;

import vsla.group.dto.GroupRegistrationReq;
import vsla.group.dto.GroupResponse;
import vsla.userManager.user.dto.UserRegistrationReq;
import vsla.userManager.user.dto.UserResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRegistrationReq groupReq);

    GroupResponse updateGroup(GroupRegistrationReq groupReq);

    UserResponse addMember(UserRegistrationReq userRegistrationReq);

    List<GroupResponse> getAllGroups();

    GroupResponse myGroup();

    List<UserResponse> getAllGroupMembers(Long groupId);
}

package vsla.group;

import vsla.group.dto.GroupRegistrationReq;
import vsla.group.dto.GroupResponse;
import vsla.group.dto.MemberReq;
import vsla.group.dto.MemberResponse;
import vsla.userManager.user.dto.UserResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRegistrationReq groupReq);

    GroupResponse updateGroup(GroupRegistrationReq groupReq);

    UserResponse addMember(MemberReq memberReq);

    List<GroupResponse> getAllGroups();

    GroupResponse myGroup();

    MemberResponse getAllGroupMembers(Long groupId);
}

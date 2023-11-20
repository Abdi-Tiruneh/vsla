package vsla.group;

import vsla.group.dto.*;
import vsla.userManager.user.dto.UserResponse;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRegistrationReq groupReq);
    GroupResponse updateGroup(GroupRegistrationReq groupReq);
    UserResponse addMember(MemberReq memberReq);
    UserResponse editMember(UpdateMemberReq updateMemberReq,Long userId);

    UserResponse deleteMember(Long userId);

    List<GroupResponse> getAllGroupsByOrganization(Long organizationId);
    List<GroupResponse> getAllGroupsByProject(Long projectId);

    GroupResponse myGroup();
    MemberResponse getAllGroupMembers(Long groupId);
    List<MembersDto> getMembers(Long groupId);
}

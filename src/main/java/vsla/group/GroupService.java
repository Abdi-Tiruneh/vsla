package vsla.group;

import vsla.group.dto.GroupRegistrationReq;
import vsla.group.dto.GroupResponse;
import vsla.group.dto.MemberReq;
import vsla.group.dto.MemberResponse;
import vsla.group.dto.MembersDto;
import vsla.group.dto.UpdateMemberReq;
import vsla.userManager.user.dto.UserResponse;

import java.lang.reflect.Member;
import java.util.List;

public interface GroupService {
    GroupResponse createGroup(GroupRegistrationReq groupReq);

    GroupResponse updateGroup(GroupRegistrationReq groupReq);

    UserResponse addMember(MemberReq memberReq);
    UserResponse editMember(UpdateMemberReq updateMemberReq,Long userId);
    UserResponse deleteMember(Long userId);
    List<GroupResponse> getAllGroups();

    GroupResponse myGroup();

    MemberResponse getAllGroupMembers(Long groupId);
    List<MembersDto> getMembers(Long groupId);
}

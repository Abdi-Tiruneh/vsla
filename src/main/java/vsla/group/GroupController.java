package vsla.group;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.group.dto.GroupRegistrationReq;
import vsla.group.dto.GroupResponse;
import vsla.group.dto.MemberReq;
import vsla.group.dto.MemberResponse;
import vsla.group.dto.MembersDto;
import vsla.group.dto.UpdateMemberReq;
import vsla.userManager.user.dto.UserResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@Tag(name = "Group Group API.")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/mine")
    public ResponseEntity<GroupResponse> getMe() {
        return ResponseEntity.ok(groupService.myGroup());
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAllGroups());
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<MemberResponse> getAllGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getAllGroupMembers(groupId));
    }
     @GetMapping("/{groupId}/constributors")
    public ResponseEntity<List<MembersDto>> getConconstributorsMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getMembers(groupId));
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody @Valid GroupRegistrationReq groupReq) {
        GroupResponse group = groupService.createGroup(groupReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(group);
    }

    @PutMapping
    public ResponseEntity<GroupResponse> updateGroup(@RequestBody @Valid GroupRegistrationReq groupReq) {
        return ResponseEntity.ok(groupService.updateGroup(groupReq));
    }

    @PostMapping("/add-member")
    public ResponseEntity<UserResponse> addMember(@RequestBody @Valid MemberReq memberReq) {
        return ResponseEntity.ok(groupService.addMember(memberReq));
    }
    @PutMapping("/edit-member/{userId}")
    public ResponseEntity<UserResponse> editMember(@RequestBody @Valid UpdateMemberReq updateMemberReq,@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.editMember(updateMemberReq,userId));
    }
    @DeleteMapping("/delete-member/{userId}")
    public ResponseEntity<UserResponse> deleteMember(@PathVariable Long userId) {
        return ResponseEntity.ok(groupService.deleteMember(userId));
    }
}



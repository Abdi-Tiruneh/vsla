package vsla.group;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.group.dto.GroupRegistrationReq;
import vsla.group.dto.GroupResponse;
import vsla.userManager.user.dto.UserRegistrationReq;
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
    public ResponseEntity<List<UserResponse>> getAllGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getAllGroupMembers(groupId));
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
    public ResponseEntity<UserResponse> addMember(@RequestBody @Valid UserRegistrationReq userRegistrationReq) {
        return ResponseEntity.ok(groupService.addMember(userRegistrationReq));
    }
}



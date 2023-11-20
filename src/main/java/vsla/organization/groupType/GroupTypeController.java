package vsla.organization.groupType;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.organization.groupType.dto.GroupTypeReq;
import vsla.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group-types")
@Tag(name = "Group Type API.")
public class GroupTypeController {
    private final GroupTypeService groupTypeService;

    public GroupTypeController(GroupTypeService groupTypeService) {
        this.groupTypeService = groupTypeService;
    }

    @PostMapping
    public ResponseEntity<GroupType> createGroupType(@RequestBody @Valid GroupTypeReq registrationReq) {
        GroupType createdStore = groupTypeService.createGroupType(registrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{groupTypeId}")
    public ResponseEntity<GroupType> updateGroupType(
            @PathVariable Long groupTypeId, @RequestBody @Valid GroupTypeReq updateReq) {
        return ResponseEntity.ok(groupTypeService.updateGroupType(groupTypeId, updateReq));
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<GroupType>> getAllGroupTypes(@PathVariable Long organizationId) {
        return ResponseEntity.ok(groupTypeService.getAllGroupTypesByOrganization(organizationId));
    }

    @DeleteMapping({"/{groupTypeId}"})
    public ResponseEntity<ApiResponse> deleteGroupType(@PathVariable Long groupTypeId) {
        groupTypeService.deleteGroupType(groupTypeId);
        return ApiResponse.success("Group Type deleted successfully");
    }
}
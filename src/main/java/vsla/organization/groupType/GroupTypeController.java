package vsla.organization.groupType;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.organization.groupType.dto.GroupTypeReq;
import vsla.organization.organization.Organization;
import vsla.organization.organization.OrganizationService;
import vsla.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/group-types")
@Tag(name = "Group Type API.")
public class GroupTypeController {
    private final GroupTypeService groupTypeService;
    private final GroupTypeFeignClient groupTypeFeignClient;
    private final OrganizationService organizationService;
    private final GroupTypeRepository groupTypeRepository;
     @Autowired
    private EntityManager entityManager;
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
    @Transactional
    @GetMapping("/by-organization/{organizationId}")
    public ResponseEntity<List<GroupType>> getAllGroupTypes(@PathVariable Long organizationId) {
        Organization organization=organizationService.getOrganizationById(organizationId);
        List<GroupType> groupTypes=groupTypeFeignClient.getAllGroupTypeByOrganization(organizationId);
        List<GroupType> updatedGroupTypes=new ArrayList<GroupType>();
        groupTypes.stream().forEach(g->{
            GroupType groupType= new GroupType();
            groupType.setCreatedAt(g.getCreatedAt());
            groupType.setDeleted(false);
            groupType.setGroupTypeId(g.getGroupTypeId());
            groupType.setGroupTypeName(g.getGroupTypeName());
            groupType.setOrganization(organization);
            groupType.setStatus(g.getStatus());
            groupType.setUpdatedAt(g.getUpdatedAt());
            updatedGroupTypes.add(groupType);
        });
        entityManager.createNativeQuery("DELETE FROM group_types  WHERE group_type_id NOT IN (SELECT group_type_id FROM groups)").executeUpdate();
        updatedGroupTypes.stream().forEach(ug->{
            groupTypeRepository.save(ug);
        });
        return ResponseEntity.ok(groupTypeService.getAllGroupTypesByOrganization(organizationId));
    }

    @DeleteMapping({"/{groupTypeId}"})
    public ResponseEntity<ApiResponse> deleteGroupType(@PathVariable Long groupTypeId) {
        groupTypeService.deleteGroupType(groupTypeId);
        return ApiResponse.success("Group Type deleted successfully");
    }
}
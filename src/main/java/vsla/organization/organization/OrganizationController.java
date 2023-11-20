package vsla.organization.organization;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/organizations")
@Tag(name = "Organization API.")
public class OrganizationController {
    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody @Valid Organization registrationReq) {
        Organization createdStore = organizationService.createOrganization(registrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(
            @PathVariable Long organizationId, @RequestBody @Valid Organization updateReq) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationId, updateReq));
    }

    @GetMapping
    public ResponseEntity<List<Organization>> getAllOrganizations() {
        return ResponseEntity.ok(organizationService.getAllOrganizations());
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable Long organizationId) {
        return ResponseEntity.ok(organizationService.getOrganizationById(organizationId));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<ApiResponse> deleteOrganization(@PathVariable Long organizationId) {
        organizationService.deleteOrganization(organizationId);
        return ApiResponse.success("Organization deleted successfully");
    }
}
package vsla.organization.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vsla.organization.project.dto.ProjectReq;
import vsla.utils.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Project API.")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody @Valid ProjectReq registrationReq) {
        Project createdStore = projectService.createProject(registrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long projectId, @RequestBody @Valid ProjectReq updateReq) {
        return ResponseEntity.ok(projectService.updateProject(projectId, updateReq));
    }

    @GetMapping("/by-organization/{organizationId}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable Long organizationId) {
        return ResponseEntity.ok(projectService.getAllProjectsByOrganization(organizationId));
    }

    @DeleteMapping({"/{projectId}"})
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.success("Project deleted successfully");
    }
}
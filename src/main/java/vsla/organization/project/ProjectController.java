package vsla.organization.project;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vsla.organization.organization.Organization;
import vsla.organization.organization.OrganizationService;
import vsla.organization.project.dto.ProjectReq;
import vsla.utils.ApiResponse;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
@Tag(name = "Project API.")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectFeignClient projectFeignClient;
    private final OrganizationService organizationService;
    private final ProjectRepository projectRepository;
    @Autowired
    private EntityManager entityManager;
    
 

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
    @Transactional
    @GetMapping("/by-organization/{organizationId}")
    public ResponseEntity<List<Project>> getAllProjects(@PathVariable Long organizationId) {
        Organization organization=organizationService.getOrganizationById(organizationId);
        List<Project> projects=projectFeignClient.getAllProjectsByOrganization(organizationId);
        List<Project> updatedProjects=new ArrayList<Project>();
        projects.stream().forEach(p->{
            Project project= new Project();
            project.setProjectId(p.getProjectId());
            project.setProjectName(p.getProjectName());
            project.setCreatedAt(p.getCreatedAt());
            project.setOrganization(organization);
            project.setDeleted(false);
            project.setStatus(p.getStatus());
            project.setUpdatedAt(p.getUpdatedAt());
            updatedProjects.add(project);
        });
        entityManager.createNativeQuery("DELETE FROM projects WHERE project_id NOT IN (SELECT project_id FROM groups)").executeUpdate();
        updatedProjects.stream().forEach(up->{
            projectRepository.save(up);
        });
        return ResponseEntity.ok(projectService.getAllProjectsByOrganization(organizationId));
    }

    @DeleteMapping({"/{projectId}"})
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ApiResponse.success("Project deleted successfully");
    }
}
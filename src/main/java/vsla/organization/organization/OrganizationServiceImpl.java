package vsla.organization.organization;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vsla.exceptions.customExceptions.ResourceNotFoundException;
import vsla.utils.Status;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Organization createOrganization(Organization registrationReq) {
        // Create a new Project
        Organization organization = new Organization();
        organization.setOrganizationName(registrationReq.getOrganizationName());
        organization.setOrganizationStatus(Status.ACTIVE);

        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long organizationId, Organization updateReq) {
        Organization organization = getOrganizationById(organizationId);

        if (updateReq.getOrganizationName() != null)
            organization.setOrganizationName(updateReq.getOrganizationName());

        if (updateReq.getOrganizationStatus() != null)
            organization.setOrganizationStatus(updateReq.getOrganizationStatus());

        return organizationRepository.save(organization);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        
        return organizationRepository.findAll(Sort.by(Sort.Order.asc("organizationId")));
    }

    @Override
    public Organization getOrganizationById(Long organizationId) {
        return organizationRepository.findOrganizationByOrganizationId(organizationId);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        getOrganizationById(organizationId);
        organizationRepository.deleteById(organizationId);
    }

}

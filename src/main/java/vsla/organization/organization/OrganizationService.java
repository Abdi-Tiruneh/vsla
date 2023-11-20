package vsla.organization.organization;

import java.util.List;

public interface OrganizationService {
    Organization createOrganization(Organization registrationReq);

    Organization updateOrganization(Long organizationId, Organization updateReq);

    List<Organization> getAllOrganizations();

    Organization getOrganizationById(Long organizationId);

    void deleteOrganization(Long organizationId);
}

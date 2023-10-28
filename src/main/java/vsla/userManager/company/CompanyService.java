package vsla.userManager.company;


import java.util.List;

public interface CompanyService {
    Company addCompany(Company company);

    Company getCompanyById(Short companyId);

    List<Company> getAllCompanies();

}

package vsla.userManager.company;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vsla.exceptions.customExceptions.ResourceNotFoundException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Company addCompany(Company reqBody) {
        Company company = new Company();
        company.setCompanyId(reqBody.getCompanyId()); // to sync with admin database
        company.setCompanyName(reqBody.getCompanyName());

        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Short companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll(Sort.by(Sort.Order.asc("companyId")));
    }

}

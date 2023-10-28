package vsla;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vsla.userManager.company.Company;
import vsla.userManager.company.CompanyRepository;
import vsla.userManager.role.Role;
import vsla.userManager.role.RoleRepository;
import vsla.userManager.user.UserRepository;
import vsla.userManager.user.UserStatus;
import vsla.userManager.user.Users;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "database", name = "seed", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    /**
     * Initializes the database with preloaded data upon application startup.
     */
    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            try {
                // Create and save roles
                List<Role> roles = createUserRole();
                roles = roleRepository.saveAll(roles);

                // Create and save company
                Company company = createCompany();
                companyRepository.save(company);

                // Create and save user
                Users johnDoe = createUser(roles.get(0), company);
                userRepository.save(johnDoe);

                log.info("ApplicationRunner => Preloaded company, roles and admin user");
            } catch (Exception ex) {
                log.error("ApplicationRunner Preloading Error: {}", ex.getMessage());
                throw new RuntimeException("ApplicationRunner Preloading Error ", ex);
            }
        };
    }

    private List<Role> createUserRole() {
        Role groupAdmin = new Role("GROUP_ADMIN", "Manages all aspects of the Group.");
        Role admin = new Role("ADMIN", "Manages all aspects of the App.");
        Role user = new Role("USER", "App user");

        return List.of(groupAdmin, admin, user);
    }

    private Users createUser(Role role, Company company) {
        return Users.builder()
                .password(passwordEncoder.encode("1234"))
                .fullName("John Doe")
                .username("0912345678")
                .role(role)
                .company(company)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    private Company createCompany() {
        Company company = new Company();
        company.setCompanyName("Ethio Care");

        return company;
    }
}
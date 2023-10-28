package vsla.userManager.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vsla.exceptions.customExceptions.ResourceAlreadyExistsException;
import vsla.userManager.address.Address;
import vsla.userManager.address.AddressService;
import vsla.userManager.company.Company;
import vsla.userManager.company.CompanyService;
import vsla.userManager.role.Role;
import vsla.userManager.role.RoleService;
import vsla.userManager.user.dto.UserMapper;
import vsla.userManager.user.dto.UserRegistrationReq;
import vsla.userManager.user.dto.UserResponse;
import vsla.userManager.user.dto.UserUpdateReq;
import vsla.utils.CurrentlyLoggedInUser;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final CompanyService companyService;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse register(UserRegistrationReq userReq) {
        if (userRepository.findByUsername(userReq.getPhoneNumber()).isPresent())
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        Users user = createUser(userReq);
        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    private Users createUser(UserRegistrationReq userReq) {
        Role role = roleService.getRoleById(userReq.getRoleId());
        Company company = companyService.getCompanyById(userReq.getCompanyId());
        Address address = addressService.addAddress(userReq.getAddress());

        return Users.builder()
                .username(userReq.getPhoneNumber())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .fullName(userReq.getFullName())
                .proxyEnabled(userReq.isProxyEnabled())
                .role(role)
                .address(address)
                .company(company)
                .userStatus(UserStatus.ACTIVE)
                .build();
    }

    @Override
    @Transactional
    public UserResponse editUser(UserUpdateReq updateReq) {
        Users user = currentlyLoggedInUser.getUser();

        if (updateReq.getFullName() != null)
            user.setFullName(updateReq.getFullName());

        // use phone number as username
        // Update phone number if provided and different from the current phone number
        if (updateReq.getPhoneNumber() != null && !user.getUsername().equals(updateReq.getPhoneNumber())) {
            // Check if the new Phone Number is already taken
            if (userRepository.findByUsername(updateReq.getPhoneNumber()).isPresent())
                throw new ResourceAlreadyExistsException("Phone number is already taken");

            user.setUsername(updateReq.getPhoneNumber());
        }

        user = userRepository.save(user);
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse me() {
        Users user = currentlyLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<Users> users = userRepository.findAll(Sort.by(Sort.Order.asc("userId")));
        return users.stream().
                map(UserMapper::toUserResponse).
                toList();
    }

    @Override
    public List<UserResponse> getUsersByGroup(Long groupId) {
        List<Users> users = userRepository.findByGroupGroupId(groupId);

        return users.stream()
                .map(UserMapper::toUserResponse)
                .sorted(Comparator.comparing(UserResponse::getFullName).reversed())
                .toList();
    }

}

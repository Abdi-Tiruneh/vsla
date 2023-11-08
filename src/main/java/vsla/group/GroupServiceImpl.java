package vsla.group;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vsla.exceptions.customExceptions.BadRequestException;
import vsla.exceptions.customExceptions.ResourceAlreadyExistsException;
import vsla.group.dto.*;
import vsla.meeting.meeting.MeetingService;
import vsla.userManager.address.Address;
import vsla.userManager.address.AddressService;
import vsla.userManager.company.Company;
import vsla.userManager.role.Role;
import vsla.userManager.role.RoleService;
import vsla.userManager.user.UserRepository;
import vsla.userManager.user.UserService;
import vsla.userManager.user.UserStatus;
import vsla.userManager.user.Users;
import vsla.userManager.user.dto.UserMapper;
import vsla.userManager.user.dto.UserResponse;
import vsla.utils.CurrentlyLoggedInUser;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final AddressService addressService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MeetingService meetingService;

    @Override
    @Transactional
//    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    public GroupResponse createGroup(GroupRegistrationReq groupReq) {
        // Retrieve the currently logged-in user
        Users loggedInUser = currentlyLoggedInUser.getUser();

        // Check if the user is already a member of a group
        if (loggedInUser.getGroup() != null)
            throw new IllegalArgumentException("You are currently a member of the '" + loggedInUser.getGroup().getGroupName() + "' group.");

        // Create an address for the group
        Address address = addressService.addAddress(groupReq.getAddress());

        // Create a new group
        Group group = new Group();
        group.setGroupAdmin(loggedInUser);
        group.setGroupName(groupReq.getGroupName());
        group.setGroupSize(groupReq.getGroupSize());
        group.setEntryFee(groupReq.getEntryFee());
        group.setAddress(address);

        group = groupRepository.save(group);

        // Update the user's group membership
        updateUser(loggedInUser, group);

        //create default meeting for the group
        meetingService.createDefaultMeeting(group, groupReq.getMeetingIntervalId(), groupReq.getMeetingDate());

        return GroupMapper.toGroupResponse(group);
    }


    private void updateUser(Users user, Group group) {
        user.setGroup(group);
        userRepository.save(user);
    }

    @Override
    public GroupResponse updateGroup(GroupRegistrationReq groupReq) {
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public UserResponse addMember(MemberReq memberReq) {
        Users loggedInUser = currentlyLoggedInUser.getUser();
        // Get the user's company and group
        Company company = loggedInUser.getCompany();
        Group group = loggedInUser.getGroup();
        if (group == null)
            throw new BadRequestException("You cannot add a member to a non-existing group.");

        // Check if the phone number is already taken
        if (userRepository.findByUsername(memberReq.getPhoneNumber()).isPresent())
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        // Retrieve the user's role
        Role role = roleService.getRoleByRoleName(memberReq.getRoleName());

        // Create an address for the new user
        Address address = addressService.addAddress(memberReq.getAddress());

        // Create a new user
        Users user = Users.builder()
                .username(memberReq.getPhoneNumber())
                .password(passwordEncoder.encode(memberReq.getPassword()))
                .fullName(memberReq.getFullName())
                .gender(memberReq.getGender())
                .proxyEnabled(memberReq.isProxyEnabled())
                .role(role)
                .group(group)
                .address(address)
                .company(company)
                .userStatus(UserStatus.ACTIVE)
                .build();

        // Save the new user to the database
        user = userRepository.save(user);

        // Map the user to a response object
        return UserMapper.toUserResponse(user);
    }


    @Override
    public List<GroupResponse> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        return groups.stream()
                .map(GroupMapper::toGroupResponse)
                .toList();
    }

    @Override
    public MemberResponse getAllGroupMembers(Long groupId) {
        List<Users> users = userService.getUsersByGroup(groupId);

        return MemberResponse.toResponse(users);
    }


    @Override
    public GroupResponse myGroup() {
        Group group = currentlyLoggedInUser.getUser().getGroup();
        return GroupMapper.toGroupResponse(group);
    }
}

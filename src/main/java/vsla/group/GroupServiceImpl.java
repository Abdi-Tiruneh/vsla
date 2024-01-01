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
import vsla.organization.groupType.GroupType;
import vsla.organization.groupType.GroupTypeService;
import vsla.organization.organization.Organization;
import vsla.organization.project.Project;
import vsla.organization.project.ProjectService;
import vsla.payment.Transaction.Transaction;
import vsla.payment.Transaction.TransactionRepository;
import vsla.userManager.address.Address;
import vsla.userManager.address.AddressService;
import vsla.userManager.role.Role;
import vsla.userManager.role.RoleService;
import vsla.userManager.user.UserRepository;
import vsla.userManager.user.UserService;
import vsla.userManager.user.UserStatus;
import vsla.userManager.user.Users;
import vsla.userManager.user.dto.UserMapper;
import vsla.userManager.user.dto.UserResponse;
import vsla.utils.CurrentlyLoggedInUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupTypeService groupTypeService;
    private final ProjectService projectService;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;
    private final AddressService addressService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final MeetingService meetingService;
    private final TransactionRepository transactionRepository;


    @Override
    @Transactional
    // @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    public GroupResponse createGroup(GroupRegistrationReq groupReq) {
        // Retrieve the currently logged-in user
        Users loggedInUser = currentlyLoggedInUser.getUser();
        Organization organization = loggedInUser.getOrganization();

        // Check if the user is already a member of a group
        if (loggedInUser.getGroup() != null)
            throw new IllegalArgumentException(
                    "You are currently a member of the '" + loggedInUser.getGroup().getGroupName() + "' group.");

        // Create an address for the group
        Address address = addressService.addAddress(groupReq.getAddress());

        // Get project and project type

        Project project = projectService.getProjectById(groupReq.getProjectId());
        GroupType groupType = groupTypeService.getGroupTypeById(groupReq.getGroupTypeId());

        // Create a new group
        Group group = new Group();
        group.setGroupAdmin(loggedInUser);
        group.setGroupName(groupReq.getGroupName());
        group.setGroupSize(groupReq.getGroupSize());
        group.setEntryFee(groupReq.getEntryFee());
        group.setAddress(address);
        group.setProject(project);
        group.setGroupType(groupType);
        group.setOrganization(organization);

        group = groupRepository.save(group);

        // Update the user's group membership
        updateUser(loggedInUser, group);

        // // create default meeting for the group
        // meetingService.createDefaultMeeting(group, groupReq.getMeetingIntervalId(), groupReq.getMeetingDate());

        return GroupResponse.toGroupResponse(group);
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
        // Get the user's organization and group
        Organization organization = loggedInUser.getOrganization();
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
                .deleted(false)
                .group(group)
                .address(address)
                .organization(organization)
                .userStatus(UserStatus.ACTIVE)
                .build();

        // Save the new user to the database
        user = userRepository.save(user);

        // Map the user to a response object
        return UserMapper.toUserResponse(user);
    }

    @Override
    public List<GroupResponse> getAllGroupsByOrganization(Long organizationId) {
        List<Group> groups = groupRepository.findByOrganizationOrganizationId(organizationId);
        return groups.stream()
                .map(GroupResponse::toGroupResponse)
                .toList();
    }

    @Override
    public List<GroupResponse> getAllGroupsByProject(Long projectId) {
        List<Group> groups = groupRepository.findByProjectProjectId(projectId);
        return groups.stream()
                .map(GroupResponse::toGroupResponse)
                .toList();
    }

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public MemberResponse getAllGroupMembers(Long groupId) {
        List<Users> users = userService.getUsersByGroup(groupId);
        List<Users> enabledUsers = new ArrayList<Users>();
        users.stream().forEach(u -> {
            if (u.getDeleted() == false) {
                enabledUsers.add(u);
            }
        });

        return MemberResponse.toResponse(enabledUsers);
    }

    @Override
    public GroupResponse myGroup() {
        Group group = currentlyLoggedInUser.getUser().getGroup();
        return GroupResponse.toGroupResponse(group);
    }

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public UserResponse editMember(UpdateMemberReq updateMemberReq, Long userId) {
        Users loggedInUser = currentlyLoggedInUser.getUser();
        // Get the user's organization and group
        Group group = loggedInUser.getGroup();
        if (group == null)
            throw new BadRequestException("You cannot edit a member to a non-existing group.");

        // Check if the phone number is already taken
        Users memeberToBeEdited = userRepository.findUsersByUserId(userId);
        if (memeberToBeEdited == null) {
            throw new BadRequestException("member does not exist");
        } else {
            memeberToBeEdited.setUsername("");
            userRepository.save(memeberToBeEdited);
            if (userRepository.findByUsername(updateMemberReq.getPhoneNumber()).isPresent()) {
                throw new ResourceAlreadyExistsException("Phone number is already taken");
            } else {
                memeberToBeEdited.setFullName(updateMemberReq.getFullName());
                memeberToBeEdited.setUsername(updateMemberReq.getPhoneNumber());
                memeberToBeEdited.setProxyEnabled(updateMemberReq.getProxyEnabled());
                Users editedUser = userRepository.save(memeberToBeEdited);
                return UserMapper.toUserResponse(editedUser);
            }

        }

    }

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public UserResponse deleteMember(Long userId) {
        Users loggedInUser = currentlyLoggedInUser.getUser();
        // Get the user's organization and group
        Group group = loggedInUser.getGroup();
        if (group == null)
            throw new BadRequestException("You cannot delete a member to a non-existing group.");
        Users user = userRepository.findUsersByUserId(userId);
        if (user == null) {
            throw new BadRequestException("member does not exist");
        } else {
            user.setDeleted(true);
            return UserMapper.toUserResponse(user);
        }
    }
    Double maxAmount=0.0;

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public List<MembersDto> getMembers(Long groupId) {
        List<MembersDto> members = new ArrayList<MembersDto>();
        List<Users> users = userRepository.findByGroupGroupId(groupId);
        users.stream().forEach(u -> {
            if (u.getDeleted() == false) {
                maxAmount=0.0;
                List<Transaction> transactions=transactionRepository.findTransactionByPayer(u);
                transactions.stream().forEach(ts->{
                    if(ts.getPaymentType().getPaymentTypeId()==1)
                    {
                        maxAmount+=ts.getAmount();
                    }
                });
                maxAmount=maxAmount*3;
                 int decimalPlaces = 2;

            // Create a DecimalFormat object with the desired pattern
            DecimalFormat decimalFormat = new DecimalFormat("#." + "0".repeat(decimalPlaces));

            // Format the double value to a string with the specified number of decimal
            // places
            String formatted = decimalFormat.format(maxAmount);
            // Parse the formatted string back into a double
             Double result = Double.parseDouble(formatted);
                MembersDto member = new MembersDto();
                member.setUserId(u.getUserId().toString());
                member.setFullName(u.getFullName());
                member.setGender(u.getGender());
                member.setProxy(u.getProxyEnabled().toString());
                member.setMaxAmount(result.toString());
                Optional<Integer> highestRound = transactions.stream()
                        .filter(t->t.getPaymentType().getPaymentTypeId()==1)
                        .map(Transaction::getRound)
                        .max(Integer::compare);

                int roundValue = highestRound.orElse(0); // provide a default value if the optional is empty
                member.setRound(String.valueOf(roundValue+1));

                members.add(member);
            }
        });
        return members;
    }
    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public List<MembersDto> getMembersForSocial(Long groupId) {
        List<MembersDto> members = new ArrayList<MembersDto>();
        List<Users> users = userRepository.findByGroupGroupId(groupId);
        users.stream().forEach(u -> {
            if (u.getDeleted() == false) {
                MembersDto member = new MembersDto();
                member.setUserId(u.getUserId().toString());
                member.setFullName(u.getFullName());
                member.setGender(u.getGender());
                member.setProxy(u.getProxyEnabled().toString());
                List<Transaction> transactions = transactionRepository.findTransactionByPayer(u);

                Optional<Integer> highestRound = transactions.stream()
                        .filter(t->t.getPaymentType().getPaymentTypeId()==4)
                        .map(Transaction::getRound)
                        .max(Integer::compare);

                int roundValue = highestRound.orElse(0); // provide a default value if the optional is empty
                member.setRound(String.valueOf(roundValue+1));

                members.add(member);
            }
        });
        return members;
    }
}

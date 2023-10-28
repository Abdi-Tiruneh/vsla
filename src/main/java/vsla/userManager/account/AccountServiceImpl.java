package vsla.userManager.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vsla.exceptions.customExceptions.BadRequestException;
import vsla.userManager.account.dto.ChangePassword;
import vsla.userManager.user.UserRepository;
import vsla.userManager.user.Users;
import vsla.utils.ApiResponse;
import vsla.utils.CurrentlyLoggedInUser;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CurrentlyLoggedInUser currentlyLoggedInUser;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword changePassword) {
        Users user = currentlyLoggedInUser.getUser();
        validateOldPassword(user, changePassword.getOldPassword());
        user.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
    }

    private void validateOldPassword(Users user, String oldPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isPasswordMatch)
            throw new BadRequestException("Incorrect old Password!");
    }

}

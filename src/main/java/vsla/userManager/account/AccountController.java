package vsla.userManager.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vsla.userManager.account.dto.ChangePassword;
import vsla.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Account API.")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PutMapping({"/change-password"})
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePassword changePassword) {
        return accountService.changePassword(changePassword);
    }

}



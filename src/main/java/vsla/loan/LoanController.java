package vsla.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vsla.loan.dto.LoanPageDto;


@RestController
@RequestMapping("/api/v1/Loan")
@RequiredArgsConstructor
public class LoanController {
    @Autowired
    private final LoanService loanService;

    @GetMapping("/LoanPage")
    LoanPageDto getLoanPageData() {
        return loanService.getLoanPageData();
    }
}

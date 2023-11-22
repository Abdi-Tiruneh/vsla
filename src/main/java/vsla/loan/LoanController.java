package vsla.loan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vsla.loan.dto.LoanAddRequestDto;
import vsla.loan.dto.LoanPageDto;
import vsla.payment.Transaction.dto.SuccessResponse;

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

    @PostMapping("/Add/{userId}")
     public ResponseEntity<SuccessResponse>addLoan(@RequestBody LoanAddRequestDto loan,@PathVariable Long userId) {
     loanService.addLoan(loan,userId);
      SuccessResponse response = new SuccessResponse("loan requested succesfully","success");
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }
    @PutMapping("/edit/{loanId}")
     public ResponseEntity<SuccessResponse>editLoan(@PathVariable Long loanId) {
      loanService.approveLoan(loanId);
      SuccessResponse response = new SuccessResponse("loan approved succesfully","success");
            return new ResponseEntity<SuccessResponse>(response, HttpStatus.OK);
    }

}

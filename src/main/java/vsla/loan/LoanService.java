package vsla.loan;


import vsla.loan.dto.LoanAddRequestDto;
import vsla.loan.dto.LoanPageDto;


public interface LoanService {
    LoanPageDto getLoanPageData();
    Loan addLoan(LoanAddRequestDto tempLoan, Long userId);
    Loan approveLoan(Long loanId);
    Loan repayLoan(Long loanId);
}

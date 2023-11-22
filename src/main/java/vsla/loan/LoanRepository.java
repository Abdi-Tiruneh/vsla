package vsla.loan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vsla.group.Group;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findLoanByGroup(Group group);
    Loan findByLoanId(Long loanId);
}

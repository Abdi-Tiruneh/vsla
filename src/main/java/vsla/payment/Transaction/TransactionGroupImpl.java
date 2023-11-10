package vsla.payment.Transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vsla.group.Group;
import vsla.group.GroupRepository;
import vsla.payment.Transaction.dto.InnerTransactionPage;
import vsla.payment.Transaction.dto.TransactionPage;

@Service
@RequiredArgsConstructor
public class TransactionGroupImpl implements TransactionService {
    private final GroupRepository groupRepository;
    private final TransactionRepository transactionRepository;

    Double roundPaymentAmount = 0.0;
    Double loanDespersalAmount = 0.0;
    Double loanRepaymnetAmount = 0.0;

    @Override
    public TransactionPage getTransactionByGroup(Long groupId) {
        roundPaymentAmount = 0.0;
        loanDespersalAmount = 0.0;
        loanRepaymnetAmount = 0.0;
        Group group = groupRepository.findByGroupId(groupId);
        List<Transaction> transactions = transactionRepository.findTransactionByGroup(group);

        List<InnerTransactionPage> innerTransactionPages = new ArrayList<InnerTransactionPage>();
        transactions.stream().forEach(t -> {

            if (t.getPaymentType().getPaymentTypeId() == 1) {
                roundPaymentAmount += t.getAmount();
            }
            if (t.getPaymentType().getPaymentTypeId() == 2) {
                loanDespersalAmount += t.getAmount();
            }
            if (t.getPaymentType().getPaymentTypeId() == 3) {
                loanRepaymnetAmount += t.getAmount();
            }

            InnerTransactionPage innerTransactionPage = new InnerTransactionPage();
            innerTransactionPage.setName(t.getPayer().getFullName());
            innerTransactionPage.setAmount(t.getAmount().toString());
            innerTransactionPage.setStatus(t.getStatus());
            innerTransactionPages.add(innerTransactionPage);
        });
        TransactionPage transactionPage = new TransactionPage();
        transactionPage.setAllTransactions(innerTransactionPages);
        transactionPage.setRoundPayment(roundPaymentAmount.toString());
        transactionPage.setLoanDespersal(loanDespersalAmount.toString());
        transactionPage.setLoanRepaymnet(loanRepaymnetAmount.toString());
        Double total = roundPaymentAmount + loanDespersalAmount + loanRepaymnetAmount;
        transactionPage.setTotal(total.toString());
        return transactionPage;
    }

}

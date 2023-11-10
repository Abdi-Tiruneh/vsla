package vsla.payment.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vsla.payment.Transaction.dto.TransactionPage;

@RestController
@RequestMapping("/api/v1/Transactions")
@RequiredArgsConstructor
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @GetMapping("/getAllTransactions/{groupId}")
    TransactionPage getTransactions(@PathVariable Long groupId) {
        return transactionService.getTransactionByGroup(groupId);
    }
}

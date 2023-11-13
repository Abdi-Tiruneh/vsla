package vsla.payment.Transaction;



import java.util.List;

import vsla.payment.Transaction.dto.InnerTransactionPage;
import vsla.payment.Transaction.dto.TransactionPage;

public interface TransactionService {
    TransactionPage getTransactionByGroup(Long groupId);
    List<InnerTransactionPage> getSocialFundTransaction(Long groupId);
}

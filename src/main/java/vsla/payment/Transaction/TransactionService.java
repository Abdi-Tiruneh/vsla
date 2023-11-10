package vsla.payment.Transaction;



import vsla.payment.Transaction.dto.TransactionPage;

public interface TransactionService {
    TransactionPage getTransactionByGroup(Long groupId);
}

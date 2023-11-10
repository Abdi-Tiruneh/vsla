package vsla.payment.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import vsla.group.Group;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findTransactionByGroup(Group group);


}

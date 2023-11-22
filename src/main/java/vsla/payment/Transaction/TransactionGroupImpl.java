package vsla.payment.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vsla.exceptions.customExceptions.BadRequestException;
import vsla.group.Group;
import vsla.group.GroupRepository;
import vsla.group.dto.ContributionDto;
import vsla.payment.Transaction.dto.InnerTransactionPage;
import vsla.payment.Transaction.dto.TransactionPage;
import vsla.payment.paymentType.PaymentType;
import vsla.payment.paymentType.PaymentTypeRepository;
import vsla.userManager.user.UserRepository;
import vsla.userManager.user.Users;

@Service
@RequiredArgsConstructor
public class TransactionGroupImpl implements TransactionService {
    private final GroupRepository groupRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    Double roundPaymentAmount = 0.0;
    Double loanDespersalAmount = 0.0;
    Double loanRepaymnetAmount = 0.0;

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
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
            innerTransactionPage.setGender(t.getPayer().getGender());
            innerTransactionPage.setDate(t.getCreatedAt().toString());
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

    @Override
    @PreAuthorize("hasAuthority('GROUP_ADMIN')")
    @Transactional
    public List<InnerTransactionPage> getSocialFundTransaction(Long groupId) {
        Group group = groupRepository.findByGroupId(groupId);
        List<Transaction> transactions = transactionRepository.findTransactionByGroup(group);
        List<InnerTransactionPage> innerTransactionPages = new ArrayList<InnerTransactionPage>();
        transactions.stream().forEach(t -> {
            InnerTransactionPage innerTransactionPage = new InnerTransactionPage();
            if (t.getPaymentType().getPaymentTypeId() == 4||t.getPaymentType().getPaymentTypeId()==5) {
                innerTransactionPage.setAmount(t.getAmount().toString());
                innerTransactionPage.setGender(t.getPayer().getGender());
                innerTransactionPage.setDate(t.getCreatedAt().toString());
                innerTransactionPage.setName(t.getPayer().getFullName());
                innerTransactionPage.setStatus(t.getStatus());
                innerTransactionPages.add(innerTransactionPage);
            }

        });

        return innerTransactionPages;
    }

    @Override
    public Transaction addTransaction(ContributionDto contributionDto) {
       Users payer= userRepository.findUsersByUserId(contributionDto.getPayerId());
        Group group= groupRepository.findByGroupId(contributionDto.getGroupId());
        PaymentType paymentType= paymentTypeRepository.findByPaymentTypeId(contributionDto.getPayementTypeId());
        if(group==null)
        {
             throw new BadRequestException("non-existing group.");
        }
        if(payer==null)
        {
             throw new BadRequestException("non-existing user.");
        }
        if(paymentType==null)
        {
            throw new BadRequestException("non-existing Paymnet .");
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(contributionDto.getAmount());
        LocalDateTime currentDateAndTime = LocalDateTime.now();
        transaction.setCreatedAt(currentDateAndTime);
        if(paymentType.getPaymentTypeId()==4)
        {
             transaction.setStatus("Recieved");
            transaction.setDescription("social fund round payment");
        }
        if(paymentType.getPaymentTypeId()==3)
        {
             transaction.setStatus("Repaid");
            transaction.setDescription("loan repayment");
        }
        if(paymentType.getPaymentTypeId()==1)
        {
             transaction.setDescription("round payemnet");
              transaction.setStatus("Recieved");
        }
        if(paymentType.getPaymentTypeId()==2||paymentType.getPaymentTypeId()==5)
        {
             transaction.setStatus("Disbursed");
            transaction.setDescription(contributionDto.getDescription());
        }

       
        transaction.setGroup(group);
        transaction.setPayer(payer);
        transaction.setPaymentType(paymentType);
        transaction.setRound(contributionDto.getRound());
       
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public TransactionPage getTransactionByProject(Long projectId) {
       List<Group> groups=groupRepository.findByProjectProjectId(projectId);
       roundPaymentAmount = 0.0;
        loanDespersalAmount = 0.0;
        loanRepaymnetAmount = 0.0;
        List<InnerTransactionPage> innerTransactionPages = new ArrayList<InnerTransactionPage>();
        groups.stream().forEach(g->{
        List<Transaction> transactions = transactionRepository.findTransactionByGroup(g);
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
            innerTransactionPage.setGender(t.getPayer().getGender());
            innerTransactionPage.setAmount(t.getAmount().toString());
            innerTransactionPage.setDate(t.getCreatedAt().toString());
            innerTransactionPage.setStatus(t.getStatus());
            innerTransactionPages.add(innerTransactionPage);
        });
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

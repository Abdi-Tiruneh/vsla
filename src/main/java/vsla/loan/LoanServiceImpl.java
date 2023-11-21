package vsla.loan;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vsla.loan.dto.LoanListDto;
import vsla.loan.dto.LoanPageDto;
import vsla.userManager.user.Users;
import vsla.utils.CurrentlyLoggedInUser;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final CurrentlyLoggedInUser loggedInUser;
    private final LoanRepository loanRepository;
    Double pendingAmount = 0.0;
    Double activeAmount = 0.0;
    Double repaidAmount = 0.0;
    Double lostAmount = 0.0;

    @Override
    public LoanPageDto getLoanPageData() {
        pendingAmount = 0.0;
        activeAmount = 0.0;
        repaidAmount = 0.0;
        lostAmount = 0.0;
        Users user = loggedInUser.getUser();
        List<Loan> loans = loanRepository.findLoanByGroup(user.getGroup());
        List<LoanListDto> loanListDtos = new ArrayList<LoanListDto>();
        loans.stream().forEach(l -> {
            LoanListDto loanListDto = new LoanListDto();
            loanListDto.setAmount(l.getAmount().toString());
            loanListDto.setRequester(l.getLoanRequester().getFullName());
            loanListDto.setStatus(l.getStatus());
            loanListDto.setUpdatedDate(l.getUpdatedAt().toString());
            if (l.getStatus().equals("pending")) {
                pendingAmount += l.getAmount();
            }
            if (l.getStatus().equals("active")) {
                activeAmount += l.getAmount();
            }
            if (l.getStatus().equals("repaid")) {
                repaidAmount += l.getAmount();
            }
            if (l.getStatus().equals("lost")) {
                lostAmount = +l.getAmount();
            }
            loanListDtos.add(loanListDto);
        });
        LoanPageDto loanPageDto = new LoanPageDto();
        loanPageDto.setActiveValue(activeAmount.toString());
        loanPageDto.setPendingValue(pendingAmount.toString());
        loanPageDto.setRepaidValue(repaidAmount.toString());
        loanPageDto.setLostValue(lostAmount.toString());
        Double totalAmount = activeAmount + pendingAmount + repaidAmount + lostAmount;
        loanPageDto.setTotalValue(totalAmount.toString());

        Double activePercentage = (activeAmount * 100) / totalAmount;
        int decimalPlaces = 2;

        // Create a DecimalFormat object with the desired pattern
        DecimalFormat decimalFormat = new DecimalFormat("#." + "0".repeat(decimalPlaces));

        // Format the double value to a string with the specified number of decimal
        // places
        String formattedActive = decimalFormat.format(activePercentage);
        // Parse the formatted string back into a double
        Double resultActive = Double.parseDouble(formattedActive);
        loanPageDto.setActivePercent(resultActive.toString());

        Double pendingPercentage = (pendingAmount * 100) / totalAmount;
        String formattedPending = decimalFormat.format(pendingPercentage);
        // Parse the formatted string back into a double
        Double resultPending = Double.parseDouble(formattedPending);
        loanPageDto.setPendingPercent(resultPending.toString());

        Double repaidPercentage = (repaidAmount * 100) / totalAmount;
         String formattedRepaid= decimalFormat.format(repaidPercentage);
        // Parse the formatted string back into a double
        Double resultRepaid = Double.parseDouble(formattedRepaid);
        loanPageDto.setRepaidPercent(resultRepaid.toString());

        Double lostPercentage = (lostAmount * 100) / totalAmount;
        String formattedLost= decimalFormat.format(lostPercentage);
        // Parse the formatted string back into a double
        Double resultLost = Double.parseDouble(formattedLost);
        loanPageDto.setLostPercent(resultLost.toString());

        loanPageDto.setLoanListDtos(loanListDtos);
        return loanPageDto;

    }

}

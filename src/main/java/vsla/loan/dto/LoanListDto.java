package vsla.loan.dto;

import lombok.Data;

@Data
public class LoanListDto {
    private String Requester;
    private String amount;
    private String updatedDate;
    private String status;
    private String gender;
}

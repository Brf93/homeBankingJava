package com.mindhub.homebanking.DTOs;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

public class LoanAplicationDTO {

    private Long id;
    private double amount;
    private  Integer payments;
    private String destNumber;
    private double maxAmount;

    public LoanAplicationDTO() { }

    public LoanAplicationDTO(ClientLoan clientLoan, Loan loan, Account account)
        {
            this.id = loan.getId();
            this.amount = clientLoan.getAmount();
            this.payments = clientLoan.getPayments();
            this.destNumber = account.getNumber();
        }

    public Long getId()
        {
            return id;
        }

    public double getAmount()
        {
            return amount;
        }

    public Integer getPayments()
        {
            return payments;
        }

    public String getDestNumber()
        {
            return destNumber;
        }

}

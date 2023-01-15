package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.ClientLoan;

import java.time.LocalDateTime;

public class ClientLoanDTO {

    private Long id;
    private double amount;
    private Integer payments;
    private LocalDateTime date;
    private Long loanId;

    private String loanName;

    public ClientLoanDTO(){ }

    public ClientLoanDTO(ClientLoan clientLoan)
        {
            this.id = clientLoan.getId();
            this.loanId = clientLoan.getLoan().getId();
            this.loanName = clientLoan.getLoan().getName();
            this.payments = clientLoan.getPayments();
            this.amount = clientLoan.getAmount();
            this.date = clientLoan.getDate();
        }

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }
}


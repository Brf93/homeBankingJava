package com.mindhub.homebanking.DTOs;

public class PayDTO {

    private Long accountId, cardId;
    private double amount;
    private  int cvv;
    private String cardNumber;

    public PayDTO(){}

    public PayDTO(AccountDTO accountDTO, CardDTO cardDTO, ClientDTO clientDTO, double amount){
        this.accountId = accountDTO.getId();
        this.cvv = cardDTO.getCvv();
        this.cardNumber = cardDTO.getNumber();
        this.cardId = cardDTO.getId();
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Long getCardId() {
        return cardId;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public double getAmount (){ return amount;}
}

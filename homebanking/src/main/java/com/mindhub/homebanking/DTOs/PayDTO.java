package com.mindhub.homebanking.DTOs;

public class PayDTO {

    private Long cardId;
    private double amount;
    private  int cvv;
    private String cardNumber;

    public PayDTO(){}

    public PayDTO(CardDTO cardDTO, ClientDTO clientDTO, double amount){
        this.cvv = cardDTO.getCvv();
        this.cardNumber = cardDTO.getNumber();
        this.cardId = cardDTO.getId();
        this.amount = amount;
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

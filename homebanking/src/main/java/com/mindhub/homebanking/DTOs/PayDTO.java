package com.mindhub.homebanking.DTOs;

public class PayDTO {

    private double amount;
    private  int cvv;
    private String cardNumber,description;

    public PayDTO(){}

    public PayDTO(CardDTO cardDTO, ClientDTO clientDTO, double amount, String description){
        this.cvv = cardDTO.getCvv();
        this.cardNumber = cardDTO.getNumber();
        this.amount = amount;
        this.description = description;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public double getAmount (){ return amount;}

    public String getDescription()
        {
            return description;
        }
}

package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {

    private Long id;
    private String cardHolder,number;
    private CardType cardType;
    private CardColor cardColor;
    private int cvv;
    private LocalDate thruDate,fromDate;
    private boolean isEnabled;

    public CardDTO (){ }

    public CardDTO (Card card)
        {
            this.id = card.getId();
            this.cardHolder = card.getCardHolder();
            this.number = card.getNumber();
            this.cardType = card.getCardType();
            this.cardColor = card.getCardColor();
            this.cvv = card.getCvv();
            this.thruDate = card.getThruDate();
            this.fromDate = card.getFromDate();
            this.isEnabled = card.isEnabled();
        }

    public Long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public CardType getCardType() {
        return cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public boolean isEnabled()
        {
            return isEnabled;
        }
}

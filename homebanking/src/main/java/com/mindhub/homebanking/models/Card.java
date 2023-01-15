package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String cardHolder,number;
    private CardType cardType;
    private CardColor cardColor;
    private int cvv;
    private LocalDate thruDate,fromDate;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Card (){ }

    public Card (String cardHolder, CardType cardType, CardColor cardColor, String number, int cvv, LocalDate trhuDate, LocalDate fromDate)
        {
            this.cardHolder = cardHolder;
            this.cardType = cardType;
            this.cardColor = cardColor;
            this.number = number;
            this.cvv = cvv;
            this.thruDate = trhuDate;
            this.fromDate = fromDate;
        }

    //getters
    public Long getId()
        {
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

    //setters
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }


    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}

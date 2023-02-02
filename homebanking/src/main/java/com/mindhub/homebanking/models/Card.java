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
    private boolean isEnabled;
    /*@ManyToOne(fetch = EAGER)
    @JoinColumn(name="client_id")
    private Client client;*/
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    public Card (){ }

    public Card (String cardHolder, CardType cardType, CardColor cardColor, String number, int cvv, LocalDate trhuDate, LocalDate fromDate, boolean isEnabled)
        {
            this.cardHolder = cardHolder;
            this.cardType = cardType;
            this.cardColor = cardColor;
            this.number = number;
            this.cvv = cvv;
            this.thruDate = trhuDate;
            this.fromDate = fromDate;
            this.isEnabled = isEnabled;
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
    public boolean isEnabled() {return isEnabled;}
   /* public Client getClient() {return client;}*/

    public Account getAccount() {
        return account;
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

   /* public void setClient(Client client) {
        this.client = client;
    }*/
    public void setAccount(Account account)
    {
        this.account = account;
    }
    public void setEnabled(boolean enabled)
        {
            isEnabled = enabled;
        }
}

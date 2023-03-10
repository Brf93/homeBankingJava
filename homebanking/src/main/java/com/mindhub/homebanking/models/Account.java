package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.FetchType.EAGER;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private boolean isEnabled;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Transaction> transaction = new HashSet<>();
    @OneToMany(fetch = EAGER)
    private Set<Card> cards = new HashSet<>();

    public Account() { }
    public Account(String number, LocalDateTime creationDate, Double balance, AccountType accountType, boolean isEnabled)
        {
            this.number = number;
            this.creationDate = creationDate;
            this.balance = balance;
            this.accountType = accountType;
            this.isEnabled = isEnabled;
        }

    public AccountType getAccountType()
        {
            return accountType;
        }

    public boolean isEnabled()
        {
            return isEnabled;
        }

    public Set<Card> getCard() {
        return cards;
    }

    public Long getId()
        {
            return id;
        }

    public String getNumber()
        {
            return number;
        }

    public LocalDateTime getCreationDate()
        {
            return creationDate;
        }

    public double getBalance()
        {
            return balance;
        }

    public Set<Transaction> getTransaction()
    {
        return transaction;
    }

    public void setNumber(String number)
        {
            this.number = number;
        }

    public void setCreationDate(LocalDateTime creationDate)
        {
            this.creationDate = creationDate;
        }

    public void setBalance(double balance)
        {
            this.balance = balance;
        }

    public void setClient(Client client)
        {
            this.client = client;
        }
    public void setAccountType(AccountType accountType)
        {
            this.accountType = accountType;
        }
    public void setEnabled(boolean enabled)
        {
            isEnabled = enabled;
        }
    public void addTransaction(Transaction transaction)
        {
            transaction.setAccount(this);
            this.transaction.add(transaction);
        }
    public void addCard(Card card)
    {
        card.setAccount(this);
        this.cards.add(card);
    }
}

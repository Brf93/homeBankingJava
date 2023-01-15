package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private String description;
    private LocalDateTime date;
    @Enumerated (value= EnumType.STRING)
    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Accounts_id")
    private Account account;

    public Transaction(){ }

    public Transaction (double amount, String description, LocalDateTime date, TransactionType type){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    public long getId() {
        return id;
    }
    public double getAmount() {
        return amount;
    }
    public String getDescription() {
        return description;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public TransactionType getType() {
        return type;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAccount(Account account)
        {
            this.account = account;
        }
}

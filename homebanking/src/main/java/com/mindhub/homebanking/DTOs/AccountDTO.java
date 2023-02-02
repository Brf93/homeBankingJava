package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType accountType;
    private Set<TransactionDTO> transactions = new HashSet<>();
    private boolean isEnabled;
    private Set<CardDTO> cards;

    public AccountDTO(){ }

    public AccountDTO(Account account)
    {
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.accountType = account.getAccountType();
        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.isEnabled = account.isEnabled();
        this.cards = account.getCard().stream().filter(card -> card.isEnabled()).map(card -> new CardDTO(card)).collect(Collectors.toSet());

    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getAccountType() { return accountType;}
    public Set<TransactionDTO> getTransactions()
        {
            return transactions;
        }

    public boolean isEnabled() { return  isEnabled;}

    public Set<CardDTO> getCard() {
        return cards;
    }
}

package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.EAGER;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private double amount;
    private Integer payments;
    private LocalDateTime date;
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public ClientLoan(){}

    public ClientLoan (double amount, Integer payments, LocalDateTime date, Client client, Loan loan){
        this.amount = amount;
        this.payments = payments;
        this.date = date;
        this.client = client;
        this.loan = loan;
    }

    public Long getId()
    {
        return id;
    }
    public double getAmount()
        {
            return amount;
        }
    public Integer getPayments() {
        return payments;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public Client getClient()
        {
            return client;
        }
    public Loan getLoan()
        {
            return loan;
        }
}

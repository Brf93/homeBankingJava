package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String name;
    private double maxAmount;
    @ElementCollection
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoan = new HashSet<>();

    public Loan() {}

    public Loan(String name, double maxAmount, List<Integer> payments){
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    //Getters
    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getMaxAmount()
    {
        return maxAmount;
    }

    public List<Integer> getPayments()
    {
        return payments;
    }

    public Set<ClientLoan> getClientLoan()
    {
        return clientLoan;
    }

    //Setters
    public void setName(String name)
    {
        this.name = name;
    }

    public void setMaxAmount(double maxAmount)
    {
        this.maxAmount = maxAmount;
    }

    public void setPayments(List<Integer> payments)
    {
        this.payments = payments;
    }

}

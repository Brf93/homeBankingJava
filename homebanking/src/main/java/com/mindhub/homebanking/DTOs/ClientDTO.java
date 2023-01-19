package com.mindhub.homebanking.DTOs;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.GenderType;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName, lastName, email, password;
    private GenderType gender;
    private Boolean enabled;
    private String avatar;
    private Set<AccountDTO> account = new HashSet<>();
    private Set<ClientLoanDTO> loan;
    private Set<CardDTO> card;

    public ClientDTO() { }
    public ClientDTO(Client client)
        {
            this.id = client.getId();
            this.firstName = client.getFirstName();
            this.lastName = client.getLastName();
            this.email = client.getEmail();
            this.password = client.getPassword();
            this.gender = client.getGender();
            this.enabled = client.getEnabled();
            this.avatar = client.getAvatar();
            this.account = client.getAccount().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
            this.loan = client.getClientLoan().stream().map(clientLoan -> new ClientLoanDTO(clientLoan)).collect(Collectors.toSet());
            this.card = client.getCard().stream().filter(card -> card.isEnabled()).map(card -> new CardDTO(card)).collect(Collectors.toSet());

        }

    public long getId()
        {
            return id;
        }

    public Boolean getEnabled()
        {
            return enabled;
        }

    public GenderType getGender()
        {
            return gender;
        }

    public String getFirstName()
        {
            return firstName;
        }

    public String getLastName()
        {
            return lastName;
        }

    public String getEmail()
        {
            return email;
        }

    public Set<AccountDTO> getAccount() {
        return account;
    }

    public Set<ClientLoanDTO> getClientLoan() {
        return loan;
    }

    public Set<CardDTO> getCard() {return card;}

    public String getPassword() {return password;}

    public String getAvatar() { return avatar;}


}

package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName, lastName, email, password;
    private GenderType gender;
    private boolean enabled;
    private String avatar;
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Account> account = new HashSet<>();
    @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoan = new HashSet<>();

   /* @OneToMany(mappedBy = "client",fetch = FetchType.EAGER)
    private Set<Card> card = new HashSet<>();*/

    public Client() {
        }
    public Client(String firstName, String lastName, String email, GenderType gender, Boolean enabled, String password, String avatar)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.gender = gender;
            this.enabled = enabled;
            this.avatar = avatar;
        }
    public void addAccount(Account account)
    {
        account.setClient(this);
        this.account.add(account);
    }
    /*public void addCard(Card card)
    {
        card.setClient(this);
        this.card.add(card);
    }*/
    //Getters
    public Long getId() {
        return id;
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
    public String getPassword() {
        return password;
    }
    //public Set<Card> getCard(){ return card; }
    public GenderType getGender()
    {
        return gender;
    }
    public boolean getEnabled()
    {
        return enabled;
    }
    public String getAvatar() { return avatar;}

    public Set<Account> getAccount()
    {
        return account;
    }
    public Set<ClientLoan> getClientLoan()
    {
        return clientLoan;
    }

    //Setters
    public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

    public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

    public void setEmail(String email)
        {
            this.email = email;
        }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }
}

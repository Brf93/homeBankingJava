package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


/*@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)*/
@SpringBootTest
    public class RepositoriesTest {

        @Autowired
        LoanRepository loanRepository;
        @Autowired
        ClientRepository clientRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }
        /*@Test
        public void hasPayments(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,hasItem(hasProperty("payments", contains(List.of(6,12,24,36,48,60)))));
        }*/
        @Test
        public void checkClientName(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem(hasProperty("firstName", is(not(empty())))));
        }
    }

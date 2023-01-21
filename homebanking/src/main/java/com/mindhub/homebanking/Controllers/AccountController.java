package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountService.findAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return new AccountDTO(accountService.findById(id));
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication , @RequestParam AccountType accountType) {

        Client currentClient = clientService.findByEmail(authentication.getName());

       if (currentClient.getAccount().size() == 3)
            {
                return new ResponseEntity<>("Max accounts reached", HttpStatus.FORBIDDEN);
            }
            else
                {
                    Account account = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),0.00 , accountType,true);
                    currentClient.addAccount(account);
                    accountService.saveAccount(account);
                    return new ResponseEntity<>("Account created successfuly", HttpStatus.CREATED);
               }
    }
    @PostMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> createAccount(Authentication authentication , @RequestParam Long accountId) {

        Client currentClient = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(accountId);

        if(!currentClient.getAccount().contains(account)){
            return new ResponseEntity<>("This account doesn't belong to this client", HttpStatus.FORBIDDEN);
        }

        if (account.getBalance() != 0)
        {
            return new ResponseEntity<>("You can't delete an account with founds, empty your account and try again", HttpStatus.FORBIDDEN);
        }
        else
        {
            account.setEnabled(false);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Account deleted successfuly", HttpStatus.CREATED);
        }
    }
}
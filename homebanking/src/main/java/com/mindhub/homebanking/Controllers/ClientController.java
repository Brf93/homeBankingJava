package com.mindhub.homebanking.Controllers;
import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.GenderType;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

        @Autowired
        private ClientService clientService;
        @Autowired
        private AccountService accountService;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/clients")
        public List<ClientDTO> getClients()
                {
                        return clientService.findAllClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
                }
        @GetMapping("/clients/{id}")
        public ClientDTO getClient(@PathVariable Long id)
                {
                        return new ClientDTO(clientService.findById(id));
                }
        @GetMapping("/clients/current")
        public ClientDTO getClients(Authentication authentication)
                {
                        return new ClientDTO(clientService.findByEmail(authentication.getName()));
                }
        @PostMapping("/clients")
        public ResponseEntity<Object> register(@RequestParam String firstName, @RequestParam String lastName, @RequestParam GenderType genderType , @RequestParam boolean enabled,
                @RequestParam String email, @RequestParam String password, @RequestParam String avatar, Authentication authentication) {
                if (firstName.isEmpty())
                        {
                                return new ResponseEntity<>("The first name field is empty", HttpStatus.FORBIDDEN);
                        }
                        else if(lastName.isEmpty())
                                {
                                        return new ResponseEntity<>("The last name field is empty", HttpStatus.FORBIDDEN);
                                }
                                else if(email.isEmpty())
                                        {
                                                return new ResponseEntity<>("The email field is empty", HttpStatus.FORBIDDEN);
                                        }
                                        else if(password.isEmpty())
                                                {
                                                        return new ResponseEntity<>("The password field is empty", HttpStatus.FORBIDDEN);
                                                }
                if (clientService.findByEmail(email) !=  null)
                        {
                                return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
                        }
                Client cliente = new Client(firstName, lastName, email, genderType,enabled,passwordEncoder.encode(password),avatar);
                clientService.saveClient(cliente);
                Account account = new Account("VIN-" + Utilities.getRandomNumber(10000000,99999999),Utilities.dateFormat(LocalDateTime.now()),0.00, AccountType.SAVINGS,true);
                cliente.addAccount(account);
                accountService.saveAccount(account);

                return new ResponseEntity<>("Registration ok",HttpStatus.CREATED);
        }
}
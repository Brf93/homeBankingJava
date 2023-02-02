package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.PayDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;
    
    @Transactional
    @PostMapping("/pay")
    public ResponseEntity<Object> payment (Authentication authentication, @RequestBody PayDTO payDTO) {

        Client currentClient = clientService.findByEmail(authentication.getName());
        Account originAccount = accountService.findById(payDTO.getAccountId());
        Account destAccount = accountService.findByNumberEquals("VIN-100");
        Card clientCard = cardService.findById(payDTO.getCardId());

        if(originAccount.getBalance() < 0)
        {
            return new ResponseEntity<>("Insufficient founds", HttpStatus.FORBIDDEN);
        }
        if(originAccount.getBalance() < payDTO.getAmount() )
        {
            return new ResponseEntity<>("Insufficient founds, try with an other account", HttpStatus.FORBIDDEN);
        }
        if(clientCard.getCvv() != payDTO.getCvv() || !(clientCard.getNumber().equals(payDTO.getCardNumber())))
        {
            return new ResponseEntity<>("Wrong credentials", HttpStatus.FORBIDDEN);
        }
        if (clientCard.getThruDate().isAfter(LocalDate.now()))
        {
            return new ResponseEntity<>("Your card is expired",HttpStatus.FORBIDDEN);
        }

        Transaction payTransaction = new Transaction(payDTO.getAmount(),"Payment made from :" + originAccount.getNumber(),Utilities.dateFormat(LocalDateTime.now()),TransactionType.DEBIT, originAccount.getBalance() - payDTO.getAmount());
        Transaction recieveTransaction = new Transaction(payDTO.getAmount(),"Payment made to :" + destAccount.getNumber(),Utilities.dateFormat(LocalDateTime.now()),TransactionType.CREDIT, originAccount.getBalance() + payDTO.getAmount());

        originAccount.addTransaction(payTransaction);
        originAccount.setBalance(originAccount.getBalance() - payDTO.getAmount());

        transactionService.saveTransaction(payTransaction);
        transactionService.saveTransaction(recieveTransaction);

        destAccount.addTransaction(payTransaction);
        destAccount.setBalance(destAccount.getBalance() + payDTO.getAmount());

        return new ResponseEntity<>("Payment successful", HttpStatus.OK);
    }
}

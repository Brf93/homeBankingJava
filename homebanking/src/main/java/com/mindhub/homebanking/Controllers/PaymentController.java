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
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private CardService cardService;

    private CardDTO cardDTO;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/pay")
    public ResponseEntity<Object> payment (@RequestBody PayDTO payDTO) {
        Card clientCard = cardService.findByNumber(payDTO.getCardNumber());
        Account originAccount = accountService.findById(clientCard.getAccount().getId());
        Account destAccount = accountService.findByNumberEquals("VIN-100");
        if(originAccount.getBalance() < 0)
        {
            return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
        }
        if(originAccount.getBalance() < payDTO.getAmount() )
        {
            return new ResponseEntity<>("Insufficient funds, try with another account or just top up this one", HttpStatus.FORBIDDEN);
        }
        if(clientCard.getCvv() != payDTO.getCvv() || !(clientCard.getNumber().equals(payDTO.getCardNumber())))
        {
            return new ResponseEntity<>("Wrong credentials", HttpStatus.FORBIDDEN);
        }
        if (LocalDate.now().isAfter(clientCard.getThruDate()))
        {
            return new ResponseEntity<>("Your card is expired",HttpStatus.FORBIDDEN);
        }

        Transaction payTransaction = new Transaction(payDTO.getAmount(),payDTO.getDescription(),Utilities.dateFormat(LocalDateTime.now()),TransactionType.DEBIT, originAccount.getBalance() - payDTO.getAmount());
        Transaction recieveTransaction = new Transaction(payDTO.getAmount(),payDTO.getDescription(),Utilities.dateFormat(LocalDateTime.now()),TransactionType.CREDIT, destAccount.getBalance() + payDTO.getAmount());

        originAccount.addTransaction(payTransaction);
        originAccount.setBalance(originAccount.getBalance() - payDTO.getAmount());

        destAccount.addTransaction(recieveTransaction);
        destAccount.setBalance(destAccount.getBalance() + payDTO.getAmount());

        transactionService.saveTransaction(payTransaction);
        transactionService.saveTransaction(recieveTransaction);

        return new ResponseEntity<>("Payment successful", HttpStatus.OK);
    }
}

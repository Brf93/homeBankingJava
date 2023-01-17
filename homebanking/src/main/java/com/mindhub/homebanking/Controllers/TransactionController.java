package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.TransactionDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/transaction")
    public List<TransactionDTO> getTransaction()
        {
            return transactionService.findAllTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
        }

    @GetMapping("transaction/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id)
        {
            return new TransactionDTO(transactionService.findById(id));
        }
    @Transactional
    @PostMapping("/clients/current/transaction")
    public ResponseEntity<Object> transaction (Authentication authentication, @RequestParam Double amount, @RequestParam String originNumber, @RequestParam String destNumber, @RequestParam String descr) {

        Client currentClient = clientService.findByEmail(authentication.getName());
        Account originAccount = accountService.findByNumberEquals(originNumber);
        Account destAccount = accountService.findByNumberEquals(destNumber);

        if (originNumber == null || destNumber == null)
            {
                return new ResponseEntity<>("Complete both accounts numbers", HttpStatus.FORBIDDEN);
            }
        if (originAccount ==  destAccount)
            {
                return new ResponseEntity<>("The accounts are the same", HttpStatus.FORBIDDEN);
            }
        if (originAccount == null)
            {
                return new ResponseEntity<>("Origin account not found", HttpStatus.FORBIDDEN);
            }
        if (!currentClient.getAccount().contains(originAccount))
            {
                return new ResponseEntity<>("Client doesn't have this account", HttpStatus.FORBIDDEN);
            }
        if (destAccount == null)
            {
                return new ResponseEntity<>("Destination account not found", HttpStatus.FORBIDDEN);
            }
        if (originAccount.getBalance() < amount)
            {
                return new ResponseEntity<>("Insufficient funds", HttpStatus.FORBIDDEN);
            }
        if (amount <= 0 || amount.equals(null))
        {
            return new ResponseEntity<>("Minimum amount: $1", HttpStatus.FORBIDDEN);
        }
        /*if (amount.isNaN() || originNumber.isEmpty() || destNumber.isEmpty())
            {
                return new ResponseEntity<>("Empty fields not allowed", HttpStatus.FORBIDDEN);
            }*/

        Transaction outcomingTransaction = new Transaction(amount,descr + " - Transaction to: " + destNumber,Utilities.dateFormat(LocalDateTime.now()), TransactionType.DEBIT);
        Transaction incomingTransaction = new Transaction(amount,descr + " - Transaction from: " + originNumber,Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);

        transactionService.saveTransaction(outcomingTransaction);
        transactionService.saveTransaction(incomingTransaction);

        originAccount.addTransaction(outcomingTransaction);
        originAccount.setBalance(originAccount.getBalance() - amount);

        destAccount.addTransaction(incomingTransaction);
        destAccount.setBalance(destAccount.getBalance() + amount);

        return new ResponseEntity<>("Transaction successful", HttpStatus.OK);
    }
}

package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.LoanAplicationDTO;
import com.mindhub.homebanking.DTOs.LoanDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.*;
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
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ClientLoanService clientLoanService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans()
        {
            return loanService.findAllLoans().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
        }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan (Authentication authentication, @RequestBody LoanAplicationDTO loanAplicationDTO) {

        Client currentClient = clientService.findByEmail(authentication.getName());
        Account destAccount = accountService.findByNumberEquals(loanAplicationDTO.getDestNumber());
        Loan loan = loanService.findById(loanAplicationDTO.getId());


        if(destAccount.getNumber().isEmpty() )
            {
                return new ResponseEntity<>("There cannot be empty fields", HttpStatus.FORBIDDEN);
            }
        if(destAccount.getNumber() == null )
            {
                return new ResponseEntity<>("Account not found", HttpStatus.FORBIDDEN);
            }
        if(loan == null )
            {
                return new ResponseEntity<>("Type of loan not found", HttpStatus.FORBIDDEN);
            }
        if (loanAplicationDTO.getAmount() <= 0)
            {
                return new ResponseEntity<>("Minimum amount is $1",HttpStatus.FORBIDDEN);
            }
        if (loanAplicationDTO.getPayments() <= 0)
            {
                return new ResponseEntity<>("Wrong payments quantity",HttpStatus.FORBIDDEN);
            }
        if (!(loan.getId().equals(loanAplicationDTO.getId())))
            {
                return new ResponseEntity<>("Type of loan not found",HttpStatus.FORBIDDEN);
            }

        if (loanAplicationDTO.getAmount() > loan.getMaxAmount())
            {
                return new ResponseEntity<>("You have exceeded the maximum allowed",HttpStatus.FORBIDDEN);
            }
        if (!(loan.getPayments().contains(loanAplicationDTO.getPayments())))
            {
                return new ResponseEntity<>("Payment doesn't exist",HttpStatus.FORBIDDEN);
            }

        if (!(destAccount.getNumber().contains(loanAplicationDTO.getDestNumber())))
            {
                return new ResponseEntity<>("Account doesn't exist",HttpStatus.FORBIDDEN);
            }

        if (currentClient.getAccount().stream().noneMatch(account -> account.getId().equals(destAccount.getId())))
            {
                return new ResponseEntity<>("Client doesn't have this account",HttpStatus.FORBIDDEN);
            }

        if (currentClient.getClientLoan().stream().anyMatch(loanId -> loanId.getLoan().getId().equals(loanAplicationDTO.getId())))
        {
            return new ResponseEntity<>("You can't have the same loan twice",HttpStatus.FORBIDDEN);
        }

        Transaction loanTransaction = new Transaction(loanAplicationDTO.getAmount(),loan.getName().toUpperCase() +" loan approved to: " + loanAplicationDTO.getDestNumber(), Utilities.dateFormat(LocalDateTime.now()), TransactionType.CREDIT);
        ClientLoan loanDetails = new ClientLoan((loanAplicationDTO.getAmount()*Utilities.interest(loanAplicationDTO.getPayments())),loanAplicationDTO.getPayments(),Utilities.dateFormat(LocalDateTime.now()),currentClient,loan);

        transactionService.saveTransaction(loanTransaction);
        destAccount.addTransaction(loanTransaction);
        destAccount.setBalance(destAccount.getBalance() + loanAplicationDTO.getAmount());
        clientLoanService.saveClientLoan(loanDetails);
        return new ResponseEntity<>("Transaction successful", HttpStatus.OK);
    }
}


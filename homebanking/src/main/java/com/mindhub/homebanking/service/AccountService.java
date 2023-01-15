package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    List<Account> findAllAccounts();

    Account findById(Long id);

    Account findByNumberEquals(String number);


    void saveAccount(Account account);
}

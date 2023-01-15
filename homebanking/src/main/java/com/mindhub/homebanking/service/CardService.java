package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<Card> findAllAccounts();

    void saveCards(Card card);

}

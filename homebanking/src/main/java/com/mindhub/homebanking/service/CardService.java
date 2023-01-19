package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;

import java.util.List;

public interface CardService {

    List<Card> findAllAccounts();

    Card findById(Long id);
    void saveCards(Card card);

}

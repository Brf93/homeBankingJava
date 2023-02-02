package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CardServiceImplementation implements CardService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> findAllAccounts() {
        return cardRepository.findAll();
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }
    @Override
    public Card findByNumber(String number){ return cardRepository.findByNumber(number);}

    @Override
    public void saveCards(Card card) {
        cardRepository.save(card);
    }

}

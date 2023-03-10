package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.AccountDTO;
import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.DTOs.ClientDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CardService cardService;

    private AccountDTO accountDTO;
    private ClientDTO clientDTO;

    @GetMapping("/clients/cards")
    public List<CardDTO> getCards() {
        return cardService.findAllAccounts().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication,@RequestParam CardType cardType, @RequestParam CardColor cardColor, @RequestParam Long id) {
        Client currentClient = clientService.findByEmail(authentication.getName());
        Account clientAccount = accountService.findById(id);
        if(currentClient.getAccount().contains(clientAccount))
            {
                if (clientAccount.getCard().stream().filter(card -> (card.getCardType() == cardType) && (card.isEnabled())).filter(card -> (card.getThruDate().isAfter(LocalDate.now()))).collect(Collectors.toSet()).size() < 2)
                    {
                        if(clientAccount.getCard().stream().filter(card -> card.getCardType() == cardType && (card.isEnabled())).filter(card -> (card.getThruDate().isAfter(LocalDate.now()))).map(card -> card.getCardColor()).collect(Collectors.toList()).contains(cardColor))
                            {
                                return new ResponseEntity<>("Max card color reached", HttpStatus.FORBIDDEN);
                            }
                            else
                                {
                                    Card card = new Card(currentClient.getFirstName() + " " + currentClient.getLastName(),cardType,cardColor, Utilities.getRandomNumber(1000, 9999)
                                            + " " + Utilities.getRandomNumber(1000, 9999) + " " + Utilities.getRandomNumber(1000, 9999) + " " + Utilities.getRandomNumber(1000, 9999),
                                            Utilities.getRandomNumber(100, 999), LocalDate.now().plus(5, ChronoUnit.YEARS), LocalDate.now(),true);
                                    clientAccount.addCard(card);
                                    // currentClient.addCard(card);
                                    cardService.saveCards(card);
                                }
                    }
                    else
                    {
                        return new ResponseEntity<>("Max card type reached", HttpStatus.FORBIDDEN);
                    }

            }
            return new ResponseEntity<>("Card created successfuly", HttpStatus.CREATED);
    }

    @PostMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam Long cardId) {

        Client currentClient = clientService.findByEmail(authentication.getName());
        Card cards = cardService.findById(cardId);

       /* if(!(currentClient.getAccount().stream().filter((account -> (account.getCard().contains(cards)))).collect(Collectors.toSet()).size() >= 1))
                {
                    return new ResponseEntity<>("This card doesn't belong to this client", HttpStatus.FORBIDDEN);
                }*/

                cards.setEnabled(false);
                cardService.saveCards(cards);

        return new ResponseEntity<>("Card disabled successfuly", HttpStatus.CREATED);
    }
}
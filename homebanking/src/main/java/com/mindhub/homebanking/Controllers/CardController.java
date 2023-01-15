package com.mindhub.homebanking.Controllers;

import com.mindhub.homebanking.DTOs.CardDTO;
import com.mindhub.homebanking.Utils.Utilities;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private CardService cardService;

    @RequestMapping("/clients/cards")
    public List<CardDTO> getCards() {
        return cardService.findAllAccounts().stream().map(CardDTO::new).collect(Collectors.toList());
    }

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,@RequestParam CardType cardType, @RequestParam CardColor cardColor) {
        Client currentClient = clientService.findByEmail(authentication.getName());

        if (currentClient.getCard().stream().filter(card -> card.getCardType() == cardType).collect(Collectors.toSet()).size() < 3)
            {
                if(currentClient.getCard().stream().filter(card -> card.getCardType() == cardType).map(card -> card.getCardColor()).collect(Collectors.toList()).contains(cardColor))
                    {
                        return new ResponseEntity<>("Max card color reached", HttpStatus.FORBIDDEN);
                    }
                        else
                            {
                                Card card = new Card(currentClient.getFirstName() + " " + currentClient.getLastName(),cardType,cardColor, Utilities.getRandomNumber(1000, 9999)
                                + " " + Utilities.getRandomNumber(1000, 9999) + " " + Utilities.getRandomNumber(1000, 9999) + " " + Utilities.getRandomNumber(1000, 9999),
                                Utilities.getRandomNumber(100, 999), LocalDate.now().plus(5, ChronoUnit.YEARS), LocalDate.now());
                                currentClient.addCard(card);
                                cardService.saveCards(card);
                            }
            }
                else
                    {
                        return new ResponseEntity<>("Max card type reached", HttpStatus.FORBIDDEN);
                    }

        return new ResponseEntity<>("Card created successfuly", HttpStatus.CREATED);
    }
}

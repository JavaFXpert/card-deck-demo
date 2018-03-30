package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class CardDeckController {
  private final CardDeckService cardDeckService;

  public CardDeckController(CardDeckService cardDeckService) {
    this.cardDeckService = cardDeckService;
  }

  @GetMapping("/carddeck")
  public Flux<Card> getCardDeck(boolean shuffled) {
    return cardDeckService.getAllCards(shuffled);
  }

}

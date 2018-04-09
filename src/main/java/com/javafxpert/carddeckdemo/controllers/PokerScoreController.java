package com.javafxpert.carddeckdemo.controllers;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import com.javafxpert.carddeckdemo.util.poker.ScoreHand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cards/poker")
public class PokerScoreController {
  private final CardDeckService cardDeckService;
  private final CardDeckDemoProperties cardDeckDemoProperties;

  public PokerScoreController(CardDeckService cardDeckService, CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckService = cardDeckService;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
  }

  //TODO: Add switchIfEmpty to protect case in which too many or too little cards are passed in
  @GetMapping("/identifyhand")
  public Mono<String> identifyHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
      .log()
      .map(c -> cards.replaceAll(" ", ""))
      .filter(c -> c.length() == 14)  // Five card codes at two characters each, plus 4 commas
      .flatMapMany(cardDeckService::createFluxFromCardsString)
      .map(card -> card.getSeq())
      .collectList()
      .flatMap(list -> {
        ScoreHand scoreHand = new ScoreHand(list);
        String handName = scoreHand.getRank().getName();
        return Mono.just(handName);
      });




  }
}

package com.javafxpert.carddeckdemo.controllers;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.controllers.CardDeckController;
import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class CardDeckControllerTest {
  CardDeckDemoProperties cardDeckDemoProperties = new CardDeckDemoProperties();
  CardDeckController cardDeckController = new CardDeckController(new CardDeckService(cardDeckDemoProperties), cardDeckDemoProperties);

  @Test
  void getCardDeckRiffleShuffle() {
    String cardsStrA = "AS,2S,3S,4S,5S,AH,2H,3H,4H,5H";
    StepVerifier.create(
      cardDeckController.getCardDeckRiffleShuffle(cardsStrA))
      .expectNext(new Card("AS", "null:null/images"))
      .expectNext(new Card("AH", "null:null/images"))
      .expectNext(new Card("2S", "null:null/images"))
      .expectNext(new Card("2H", "null:null/images"))
      .expectNext(new Card("3S", "null:null/images"))
      .expectNext(new Card("3H", "null:null/images"))
      .expectNext(new Card("4S", "null:null/images"))
      .expectNext(new Card("4H", "null:null/images"))
      .expectNext(new Card("5S", "null:null/images"))
      .expectNext(new Card("5H", "null:null/images"))
      .expectComplete()
      .verify();
  }


  @Test
  void retrievePokerHandName() {
    cardDeckDemoProperties.setCardimageshost("http://127.0.0.1");
    cardDeckDemoProperties.setCardimagesport("8080");
    Flux<Card> cardFlux = Flux.just(
      new Card("AC", ""),
      new Card("2C", ""),
      new Card("3C", ""),
      new Card("4C", ""),
      new Card("5C", "")
    );

    assertEquals("Straight Flush", cardDeckController.retrievePokerHandName(cardFlux));
  }
}

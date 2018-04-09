package com.javafxpert.carddeckdemo.services;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.controllers.CardDeckController;
import com.javafxpert.carddeckdemo.model.Card;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckServiceTest {
  CardDeckDemoProperties cardDeckDemoProperties = new CardDeckDemoProperties();
  CardDeckService cardDeckService = new CardDeckService(cardDeckDemoProperties);

  @Test
  void createStringFromCardFlux() {
    Flux<Card> cardFlux = Flux.just(
      new Card("AC", ""),
      new Card("2C", ""),
      new Card("3C", ""),
      new Card("4C", ""),
      new Card("5C", "")
    );

    StepVerifier.create(cardDeckService.createStringFromCardFlux(cardFlux))
      .expectNext("AC,2C,3C,4C,5C");
  }
}

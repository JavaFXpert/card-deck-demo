package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class CardDeckControllerTest {
  CardDeckDemoProperties cardDeckDemoProperties = new CardDeckDemoProperties();
  CardDeckController cardDeckController = new CardDeckController(new CardDeckService(cardDeckDemoProperties), cardDeckDemoProperties);

  String cardsStrA = "AS,2S,3S,4S,5S,AH,2H,3H,4H,5H";
  @Test
  void getCardDeckRiffleShuffle() {
    // Flux<Card> cardFlux = cardDeckController.getCardDeckRiffleShuffle(cardsStrA);
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
}

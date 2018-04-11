package com.javafxpert.carddeckdemo.model;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CardHand {
  private Flux<Card> cards;
  private Mono<String> name;

  public Flux<Card> getCards() {
    return cards;
  }

  public void setCards(Flux<Card> cards) {
    this.cards = cards;
  }

  public Mono<String> getName() {
    return name;
  }

  public void setName(Mono<String> name) {
    this.name = name;
  }
}

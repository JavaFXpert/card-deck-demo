package com.javafxpert.carddeckdemo.model;

import reactor.core.publisher.Flux;

public class CardHand {
  private Flux<Card> cards;
  private String name;

  public Flux<Card> getCards() {
    return cards;
  }

  public void setCards(Flux<Card> cards) {
    this.cards = cards;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

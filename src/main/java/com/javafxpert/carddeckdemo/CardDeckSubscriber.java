package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class CardDeckSubscriber<Card> extends BaseSubscriber<Card> {
  public void hookOnSubscribe(Subscription subscription) {
    System.out.println("Subscribed");
    request(5);
  }

  public void hookOnNext(Card card) {
    System.out.println(card);
//    request(0); // wrong according to Reactive Streams spec <--
  }
}

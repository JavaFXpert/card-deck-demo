package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Component
public class RunnerBean implements CommandLineRunner {
  private final CardDeckService cardDeckService;

  RunnerBean(CardDeckService cardDeckService) {
    this.cardDeckService = cardDeckService;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("After starting up");

//    Flux<Card> cardFlux = this.cardDeckService.getAllCards(false);


    // Demonstrates use of BaseSubscriber
//    CardDeckSubscriber<Card> cardDeckSubscriber = new CardDeckSubscriber<>();
//    cardFlux.subscribe(cardDeckSubscriber);
//    System.out.println("Requesting 3 more");
//    cardDeckSubscriber.request(3);

    // Demonstrates not using a BaseSubscriber
//    cardFlux.subscribe(System.out::println,
//            System.err::println,
//            () -> System.out.println("Complete"));


  }
}

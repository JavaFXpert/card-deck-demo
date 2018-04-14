package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.carddeck.CardDeckService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerBean implements CommandLineRunner {
  private final CardDeckService cardDeckService;

  RunnerBean(CardDeckService cardDeckService) {
    this.cardDeckService = cardDeckService;
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("After starting up");

  }
}

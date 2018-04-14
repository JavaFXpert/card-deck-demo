package com.javafxpert.carddeckdemo.poker;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;

@Component
public class DataLoaderHandFrequencies {
  private final HandFrequencyRepository handFrequencyRepository;

  public DataLoaderHandFrequencies(HandFrequencyRepository handFrequencyRepository) {
    this.handFrequencyRepository = handFrequencyRepository;
  }

  @PostConstruct
  private void loadData() {
    this.handFrequencyRepository.deleteAll().thenMany(
      Flux.just(
        new HandFrequency(PokerHand.HIGH_CARD.getName(), 0),
        new HandFrequency(PokerHand.ONE_PAIR.getName(), 0),
        new HandFrequency(PokerHand.TWO_PAIR.getName(), 0),
        new HandFrequency(PokerHand.THREE_OF_A_KIND.getName(), 0),
        new HandFrequency(PokerHand.STRAIGHT.getName(), 0),
        new HandFrequency(PokerHand.FLUSH.getName(), 0),
        new HandFrequency(PokerHand.FULL_HOUSE.getName(), 0),
        new HandFrequency(PokerHand.FOUR_OF_A_KIND.getName(), 0),
        new HandFrequency(PokerHand.STRAIGHT_FLUSH.getName(), 0),
        new HandFrequency(PokerHand.ROYAL_FLUSH.getName(), 0))
      .flatMap(s -> this.handFrequencyRepository.save(s)))
      .subscribe(System.out::println);
  }
}

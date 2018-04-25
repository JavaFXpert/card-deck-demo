package com.javafxpert.carddeckdemo.poker.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.javafxpert.carddeckdemo.poker.domain.HandFrequency;
import com.javafxpert.carddeckdemo.poker.domain.PokerHand;
import com.javafxpert.carddeckdemo.poker.repository.HandFrequencyRepository;
import reactor.core.publisher.Flux;

@Configuration
public class DataLoaderHandFrequencies {

  @Bean
  public CommandLineRunner handFrequencyDataLoadCommandLineRunner(
          HandFrequencyRepository handFrequencyRepository) {
    return (args) -> handFrequencyRepository.deleteAll()
                                            .thenMany(Flux.just(
        new HandFrequency(PokerHand.HIGH_CARD.getName(), 0, 0.0),
        new HandFrequency(PokerHand.ONE_PAIR.getName(), 0, 0.0),
        new HandFrequency(PokerHand.TWO_PAIR.getName(), 0, 0.0),
        new HandFrequency(PokerHand.THREE_OF_A_KIND.getName(), 0, 0.0),
        new HandFrequency(PokerHand.STRAIGHT.getName(), 0, 0.0),
        new HandFrequency(PokerHand.FLUSH.getName(), 0, 0.0),
        new HandFrequency(PokerHand.FULL_HOUSE.getName(), 0, 0.0),
        new HandFrequency(PokerHand.FOUR_OF_A_KIND.getName(), 0, 0.0),
        new HandFrequency(PokerHand.STRAIGHT_FLUSH.getName(), 0, 0.0),
        new HandFrequency(PokerHand.ROYAL_FLUSH.getName(), 0, 0.0)
                                            ))
        .transform(handFrequencyRepository::saveAll)
        .subscribe(System.out::println);
  }
}

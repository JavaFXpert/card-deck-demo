package com.javafxpert.carddeckdemo.poker.service;

import com.javafxpert.carddeckdemo.deck.configuration.CardDeckImagesServerProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.javafxpert.carddeckdemo.poker.domain.HandFrequency;
import com.javafxpert.carddeckdemo.poker.repository.HandFrequencyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.math.MathFlux;

import java.time.Duration;

@Service
public class PokerService {
  private final CardDeckImagesServerProperties cardDeckImagesServerProperties;
  private final HandFrequencyRepository        handFrequencyRepository;

  public PokerService(CardDeckImagesServerProperties cardDeckImagesServerProperties, HandFrequencyRepository handFrequencyRepository) {
    this.cardDeckImagesServerProperties = cardDeckImagesServerProperties;
    this.handFrequencyRepository = handFrequencyRepository;
  }

  public Mono<Void> updateHandFrequency(Mono<String> handNameMono) {
    return handFrequencyRepository
      .findById(handNameMono)
      .map(hf -> new HandFrequency(hf.getHandName(), hf.getFrequency() + 1, 0.0))
      .flatMap(handFrequencyRepository::save)
      .timeout(Duration.ofMillis(500))
      .retryWhen(t -> t.zipWith(Flux.range(0, 5)).delayElements(Duration.ofMillis(200)))
      .then();
  }

  public Flux<HandFrequency> retrieveHandFrequencies() {
    Flux<HandFrequency> handFrequencyFlux = handFrequencyRepository
        .findAll(Sort.by("frequency"));
    Mono<Long> totalFrequencies = MathFlux.sumLong(handFrequencyFlux
        .map(HandFrequency::getFrequency));

    long longTotalFrequencies = 10; // TODO: Somehow get the number at some point from the Mono<Long>

    return handFrequencyFlux.map( hf ->
        new HandFrequency(hf.getHandName(),
            hf.getFrequency(),
            hf.getFrequency() * 100 / longTotalFrequencies));
  };
}

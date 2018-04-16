package com.javafxpert.carddeckdemo.poker.service;

import com.javafxpert.carddeckdemo.deck.configuration.CardDeckImagesServerProperties;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.javafxpert.carddeckdemo.poker.domain.HandFrequency;
import com.javafxpert.carddeckdemo.poker.repository.HandFrequencyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
      .map(hf -> new HandFrequency(hf.getHandName(), hf.getFrequency() + 1))
      .flatMap(handFrequencyRepository::save)
      .timeout(Duration.ofMillis(500))
      .retryWhen(t -> t.zipWith(Flux.range(0, 5)).delayElements(Duration.ofMillis(200)))
      .then();
  }

  public Flux<HandFrequency> retrieveHandFrequencies() {
    return handFrequencyRepository
        .findAll(Sort.by("frequency"));
  }
}

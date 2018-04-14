package com.javafxpert.carddeckdemo.poker;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class PokerService {
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private final HandFrequencyRepository handFrequencyRepository;

  public PokerService(CardDeckDemoProperties cardDeckDemoProperties, HandFrequencyRepository handFrequencyRepository) {
    this.cardDeckDemoProperties = cardDeckDemoProperties;
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
}

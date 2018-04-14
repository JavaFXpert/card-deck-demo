package com.javafxpert.carddeckdemo.poker;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PokerService {
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private final HandFrequencyRepository handFrequencyRepository;

  public PokerService(CardDeckDemoProperties cardDeckDemoProperties, HandFrequencyRepository handFrequencyRepository) {
    this.cardDeckDemoProperties = cardDeckDemoProperties;
    this.handFrequencyRepository = handFrequencyRepository;
  }

  public void updateHandFrequency(Mono<String> handNameMono) {
    handFrequencyRepository
      .findById(handNameMono);
    //TODO Finish implementing method

    //Increment frequency property of handFrequency
    // Save updated handFrequency
  }
}

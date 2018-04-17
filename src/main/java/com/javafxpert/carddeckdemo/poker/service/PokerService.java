package com.javafxpert.carddeckdemo.poker.service;

import java.time.Duration;

import com.javafxpert.carddeckdemo.poker.domain.HandFrequency;
import com.javafxpert.carddeckdemo.poker.repository.HandFrequencyRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PokerService {
  private final HandFrequencyRepository        handFrequencyRepository;

  public PokerService(HandFrequencyRepository handFrequencyRepository) {
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
    return handFrequencyRepository.findAll(Sort.by("frequency"))
		    .collectList()
		    .flatMapMany(l -> {
			    long totalFrequencies = l.stream()
			                             .mapToLong(HandFrequency::getFrequency)
			                             .sum();

			    return Flux.fromStream(l.stream())
			               .map(hf -> new HandFrequency(hf.getHandName(),
					               hf.getFrequency(),
					               hf.getFrequency() * 100 / totalFrequencies));
		    });
  }
}

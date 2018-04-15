package com.javafxpert.carddeckdemo.deck.service.impl;

import com.javafxpert.carddeckdemo.deck.configuration.PokerServerProperties;
import com.javafxpert.carddeckdemo.deck.domain.Card;
import com.javafxpert.carddeckdemo.deck.service.PokerService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ProxyPokerService implements PokerService {
	private final WebClient       pokerWebClient;

	public ProxyPokerService(
			PokerServerProperties properties,
			WebClient.Builder clientBuilder) {
		this.pokerWebClient = clientBuilder.baseUrl(properties.getBaseURI())
		                                   .build();
	}

	@Override
	public Mono<String> handNameFromDeck(Flux<Card> cardFlux) {
		return pokerWebClient.post()
		                     .uri("/poker/idhand")
		                     .body(cardFlux, Card.class)
		                     .retrieve()
		                     .bodyToMono(String.class);
	}
}

package com.javafxpert.carddeckdemo.deck.service;

import com.javafxpert.carddeckdemo.deck.domain.Card;
import reactor.core.publisher.Flux;

public interface CardShufflingService {

	Flux<Card> cutCards(Flux<Card> cardFlux);

	Flux<Card> overhandShuffle(Flux<Card> cardFlux);

	Flux<Card> riffleShuffle(Flux<Card> cardFlux);

	Flux<Card> dealPokerHand(Flux<Card> cardFlux);

	Flux<Card> shuffleWell(Flux<Card> cardFlux);

	Flux<Card> randomShuffle(Flux<Card> cardFlux);
}

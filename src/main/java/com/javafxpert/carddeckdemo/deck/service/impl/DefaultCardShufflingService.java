package com.javafxpert.carddeckdemo.deck.service.impl;

import com.javafxpert.carddeckdemo.deck.domain.Card;
import com.javafxpert.carddeckdemo.deck.service.CardShufflingService;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import static java.lang.Math.*;

@Service
public class DefaultCardShufflingService implements CardShufflingService {
  private static final Comparator<Card> worthComparator = Comparator.comparingInt(Card::getWorth);

  @Override
  public Flux<Card> cutCards(Flux<Card> cardFlux) {
    return cardFlux.collectList()
			       .map(list -> Tuples.of(Flux.fromIterable(list), (int)(random() * (list.size() - 1) + 1)))
                   .flatMapMany(tuple2 ->
				        tuple2.getT1()
				              .skip(tuple2.getT2())
				              .concatWith(tuple2.getT1().take(tuple2.getT2()))
                   );
  }

  @Override
  public Flux<Card> overhandShuffle(Flux<Card> cardFlux) {
	  return cardFlux.collectList()
	                 .flatMapIterable(l -> {
		                 int maxChunk = 5;
		                 int numCardsLeft = l.size();
		                 List<Card> cardList = new ArrayList<>();

		                 while (numCardsLeft > 0) {
			                 List<Card> tempCardFlux = l.subList(0, numCardsLeft);
			                 int numCardsToTransfer = min((int) (random() * maxChunk + 1),
					                 numCardsLeft);
			                 cardList.addAll(
				                 tempCardFlux.subList(tempCardFlux.size() - numCardsToTransfer, tempCardFlux.size())
			                 );

			                 numCardsLeft -= numCardsToTransfer;
		                 }
		                 return cardList;
	                 });
  }

  /**
   *
   * Note: The floor() and ceil() methods are used to accommodate and odd number of cards
   */
  @Override
  public Flux<Card> riffleShuffle(Flux<Card> cardFlux) {
    return cardFlux.collectList()
                   .flatMapMany(l -> Flux.zip(
                        Flux.fromStream(l.stream().limit((long) (ceil(l.size() / 2.0)))),
		                    Flux.fromStream(l.stream().skip((long) (floor(l.size() / 2.0))))
                   ))
                   .flatMap(tuple2 -> Flux.just(tuple2.getT1(), tuple2.getT2()))
                   .distinct();
  }

  @Override
  public Flux<Card> dealPokerHand(Flux<Card> cardFlux) {
    return cardFlux.index()
                   .take(9)
                   .filter(t -> t.getT1() % 2 == 0)
                   .map(Tuple2::getT2)
                   .sort(worthComparator);
  }

  @Override
  public Flux<Card> shuffleWell(Flux<Card> cardFlux) {
    return cardFlux.transform(this::overhandShuffle)
                   .transform(this::riffleShuffle)
                   .transform(this::overhandShuffle)
                   .transform(this::riffleShuffle)
                   .transform(this::overhandShuffle)
                   .transform(this::riffleShuffle)
                   .transform(this::overhandShuffle)
                   .transform(this::riffleShuffle)
                   .transform(this::overhandShuffle)
                   .transform(this::overhandShuffle)
                   .transform(this::cutCards);
  }

  @Override
  public Flux<Card> randomShuffle(Flux<Card> cardFlux) {
    return cardFlux.collectList()
                   .flatMapIterable(l -> {
	                   List<Card> cardList = new ArrayList<>(l);
	                   Collections.shuffle(cardList);
	                   return cardList;
                   });
  }

}

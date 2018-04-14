package com.javafxpert.carddeckdemo.carddeck;

import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.*;

public class ShuffleUtils {
  private static final Comparator<Card> worthComparator = Comparator.comparingInt(Card::getWorth);

  public static Flux<Card> cutCards(Flux<Card> cardFlux) {
    return cardFlux.collectList()
      .map(list -> Tuples.of(Flux.fromIterable(list), (int)(random() * (list.size() - 1) + 1)))
      .flatMapMany(tuple2 ->
        tuple2.getT1()
          .skip(tuple2.getT2())
          .concatWith(tuple2.getT1()
            .take(tuple2.getT2()))
      );
  }

  public static Flux<Card> overhandShuffle(Flux<Card> cardFlux) {

	  return cardFlux.collectList()
	                 .flatMapIterable(l -> {
		                 int maxChunk = 5;
		                 int numCardsLeft = l.size();
		                 List<Card> overhandShuffledCardFlux = new ArrayList<>();

		                 while (numCardsLeft > 0) {
			                 List<Card> tempCardFlux = l.subList(0, numCardsLeft);
			                 int numCardsToTransfer = min((int) (random() * maxChunk + 1),
					                 numCardsLeft);
			                 overhandShuffledCardFlux.addAll(
				                 tempCardFlux.subList(tempCardFlux.size() - numCardsToTransfer, tempCardFlux.size())
			                 );

			                 numCardsLeft -= numCardsToTransfer;
		                 }
		                 return overhandShuffledCardFlux;
	                 });
  }

  /**
   *
   * Note: The floor() and ceil() methods are used to accommodate and odd number of cards
   */
  public static Flux<Card> riffleShuffle(Flux<Card> cardFlux) {
    return cardFlux
      .collectList()
      .flatMapMany(l -> Flux.zip(
        Flux.fromStream(l.stream().limit((long)(ceil(l.size() / 2.0)))),
        Flux.fromStream(l.stream().skip((long)(floor(l.size() / 2.0)))
      ))
      .flatMap(tuple2 -> Flux.just(tuple2.getT1(), tuple2.getT2())))
      .distinct();
  }

  public static Flux<Card> dealPokerHand(Flux<Card> cardFlux) {
    return cardFlux
      .index()
      .take(9)
      .filter(t -> t.getT1() % 2 == 0)
      .map(Tuple2::getT2)
      .sort(worthComparator);
  }

  public static Flux<Card> shuffleWell(Flux<Card> cardFlux) {
    return cardFlux.transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::cutCards);
  }

  public static Flux<Card> randomShuffle(Flux<Card> cardFlux) {

    return cardFlux.collectList()
      .flatMapIterable(l -> {
        List<Card> cardList = new ArrayList<>(l);
        Collections.shuffle(cardList);
        return cardList;
      });
  }

}

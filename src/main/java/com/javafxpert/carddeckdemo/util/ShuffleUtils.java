package com.javafxpert.carddeckdemo.util;

import com.javafxpert.carddeckdemo.model.Card;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShuffleUtils {
  private static final Comparator<Card> comparator = (c1, c2) -> c1.getWorth() - c2.getWorth();

  public static Flux<Card> cutCards(Flux<Card> cardFlux) {
    return cardFlux.collectList()
      .map(list -> Tuples.of(Flux.fromIterable(list), (int)(Math.random() * (list.size() - 1) + 1)))
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
			                 int numCardsToTransfer = Math.min((int) (Math.random() * maxChunk + 1),
					                 numCardsLeft);
			                 overhandShuffledCardFlux.addAll(
				                 tempCardFlux.subList(tempCardFlux.size() - numCardsToTransfer, tempCardFlux.size())
			                 );

			                 numCardsLeft -= numCardsToTransfer;
		                 }
		                 return overhandShuffledCardFlux;
	                 });
  }

  public static Flux<Card> riffleShuffle(Flux<Card> cardFlux) {
    return cardFlux
            .collectList()
            .flatMapMany(l -> Flux.zip(
                Flux.fromStream(l.stream().skip(l.size() / 2)),
	            Flux.fromStream(l.stream().skip(l.size() / 2))
            ))
            .flatMap(tuple2 -> Flux.just(tuple2.getT1(), tuple2.getT2()));
  }

  public static Flux<Card> dealPokerHand(Flux<Card> cardFlux) {
    return cardFlux
      .index()
      .take(9)
      .filter(t -> t.getT1() % 2 == 0)
      .map(Tuple2::getT2)
      .sort(comparator);
  }

  public static Flux<Card> shuffleWell(Flux<Card> cardFlux) {
    int totalCards = cardFlux.count().block().intValue();

    return cardFlux.transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::overhandShuffle)
      .transform(ShuffleUtils::riffleShuffle)
      .transform(ShuffleUtils::cutCards);
  }

}

package com.javafxpert.carddeckdemo.util;

import com.javafxpert.carddeckdemo.model.Card;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Comparator;

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

  /**
   * Will not work :(
   * @param cardFlux
   * @return
   */
  public static Flux<Card> overhandShuffle(Flux<Card> cardFlux) {
    return cardFlux.collectList()
      .map(list -> Tuples.of(Flux.fromIterable(list), (int)(Math.random() * (list.size() - 1) + 1)))
      .flatMapMany(tuple2 ->
        tuple2.getT1()
          .skip(tuple2.getT2())
          .concatWith(tuple2.getT1()
            .take(tuple2.getT2()))
      );
  }

  public static Flux<Card> riffleShuffle(Flux<Card> cardFlux) {
    int totalCards = cardFlux.count().block().intValue();
    Flux<Card> cutCardsA = cardFlux.take(totalCards / 2);
    Flux<Card> cutCardsB = cardFlux.takeLast(totalCards / 2);
    Flux<Tuple2<Card, Card>> tuples = Flux.zip(cutCardsA, cutCardsB);

    return tuples.flatMap(tuple2 -> Flux.just(tuple2.getT1(), tuple2.getT2()));
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

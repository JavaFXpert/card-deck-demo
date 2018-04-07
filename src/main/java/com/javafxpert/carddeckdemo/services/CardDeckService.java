package com.javafxpert.carddeckdemo.services;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.model.Card;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Comparator;

@Service
public class CardDeckService {
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private String imagesUri = "";
  private final Comparator<Card> comparator = (c1, c2) -> c1.getWorth() - c2.getWorth();

  public CardDeckService(CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckDemoProperties = cardDeckDemoProperties;
    imagesUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/images";
  }

  public Flux<Card> getNewDeck() {
    return Flux.just(
        new Card("AC", imagesUri),
        new Card("2C", imagesUri),
        new Card("3C", imagesUri),
        new Card("4C", imagesUri),
//        new Card("5C", imagesUri),
//        new Card("6C", imagesUri),
//        new Card("7C", imagesUri),
//        new Card("8C", imagesUri),
//        new Card("9C", imagesUri),
//        new Card("0C", imagesUri),
//        new Card("JC", imagesUri),
        new Card("QC", imagesUri),
        new Card("KC", imagesUri),

        new Card("AH", imagesUri),
        new Card("2H", imagesUri),
        new Card("3H", imagesUri),
        new Card("4H", imagesUri),
//        new Card("5H", imagesUri),
//        new Card("6H", imagesUri),
//        new Card("7H", imagesUri),
//        new Card("8H", imagesUri),
//        new Card("9H", imagesUri),
//        new Card("0H", imagesUri),
//        new Card("JH", imagesUri),
        new Card("QH", imagesUri),
        new Card("KH", imagesUri),

        new Card("AS", imagesUri),
        new Card("2S", imagesUri),
        new Card("3S", imagesUri),
        new Card("4S", imagesUri),
//        new Card("5S", imagesUri),
//        new Card("6S", imagesUri),
//        new Card("7S", imagesUri),
//        new Card("8S", imagesUri),
//        new Card("9S", imagesUri),
//        new Card("0S", imagesUri),
//        new Card("JS", imagesUri),
        new Card("QS", imagesUri),
        new Card("KS", imagesUri),

        new Card("AD", imagesUri),
        new Card("2D", imagesUri),
        new Card("3D", imagesUri),
        new Card("4D", imagesUri),
//        new Card("5D", imagesUri),
//        new Card("6D", imagesUri),
//        new Card("7D", imagesUri),
//        new Card("8D", imagesUri),
//        new Card("9D", imagesUri),
//        new Card("0D", imagesUri),
//        new Card("JD", imagesUri),
        new Card("QD", imagesUri),
        new Card("KD", imagesUri));
  }

  public Flux<Card> cutCards(Flux<Card> cardFlux) {
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
  public Flux<Card> overhandShuffle(Flux<Card> cardFlux) {
    int totalCards = cardFlux.count().block().intValue();
    int maxChunk = 5;
    int numCardsLeft = totalCards;
    Flux<Card> overhandShuffledCardFlux = Flux.empty();

    while (numCardsLeft > 0) {
      Flux<Card> tempCardFlux = cardFlux.take(numCardsLeft);
      int numCardsToTransfer = Math.min((int)(Math.random() * maxChunk + 1), numCardsLeft);
      overhandShuffledCardFlux = Flux.concat(overhandShuffledCardFlux, tempCardFlux.takeLast(numCardsToTransfer));

      numCardsLeft -= numCardsToTransfer;
    }
    return overhandShuffledCardFlux;
  }

  public Flux<Card> riffleShuffle(Flux<Card> cardFlux) {
    int totalCards = cardFlux.count().block().intValue();
    Flux<Card> cutCardsA = cardFlux.take(totalCards / 2);
    Flux<Card> cutCardsB = cardFlux.takeLast(totalCards / 2);
    Flux<Tuple2<Card, Card>> tuples = Flux.zip(cutCardsA, cutCardsB);

    return tuples.flatMap(tuple2 -> Flux.just(tuple2.getT1(), tuple2.getT2()));
  }

  public Flux<Card> dealPokerHand(Flux<Card> cardFlux) {
    return cardFlux
            .index()
            .take(9)
            .filter(t -> t.getT1() % 2 == 0)
            .map(Tuple2::getT2)
            .sort(comparator);
  }

  public Flux<Card> shuffleWell(Flux<Card> cardFlux) {
    int totalCards = cardFlux.count().block().intValue();

    return cardFlux.transform(this::overhandShuffle)
        .transform(this::riffleShuffle)
        .transform(this::overhandShuffle)
        .transform(this::riffleShuffle)
        .transform(this::overhandShuffle)
        .transform(this::riffleShuffle)
        .transform(this::cutCards);
  }

  public Flux<Card> createFluxFromCardsString(String cardStr) {
    String[] cardStrArray = cardStr.split(",");
    Flux<Card> cardFlux = Flux.fromArray(cardStrArray)
        .map(code -> new Card(code, imagesUri));
    return cardFlux;
  }

}

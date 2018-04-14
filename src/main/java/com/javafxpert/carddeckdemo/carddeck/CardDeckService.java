package com.javafxpert.carddeckdemo.carddeck;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.poker.HandFrequency;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class CardDeckService {
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private final CardDeckRepository cardDeckRepository;
  private String imagesUri = "";
  private static final Comparator<Card> seqComparator = Comparator.comparing(Card::getId);

  public CardDeckService(CardDeckDemoProperties cardDeckDemoProperties,
                         CardDeckRepository cardDeckRepository) {
    this.cardDeckDemoProperties = cardDeckDemoProperties;
    this.cardDeckRepository = cardDeckRepository;

    imagesUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/images";
  }


  public Flux<Card> getNewDeck() {
    return cardDeckRepository.findAll().sort(seqComparator);
  }

  public void updateHandFrequency(HandFrequency handFrequency) {
    //HandFrequency handFrequency =
  }

  public Flux<Card> createFluxFromCardsString(String cardStr) {
    String[] cardStrArray = cardStr.split(",");
    Flux<Card> cardFlux = Flux.fromArray(cardStrArray)
        .map(code -> new Card(code, imagesUri));
    return cardFlux;
  }

  public Mono<String> createStringFromCardFlux(Flux<Card> cardFlux) {
    return cardFlux.map(card -> card.getCode())
        .collect(Collectors.joining(","));
  }
}

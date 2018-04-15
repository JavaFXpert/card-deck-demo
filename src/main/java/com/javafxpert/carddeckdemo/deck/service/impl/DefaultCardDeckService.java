package com.javafxpert.carddeckdemo.deck.service.impl;

import org.springframework.stereotype.Service;

import com.javafxpert.carddeckdemo.deck.configuration.CardDeckImagesServerProperties;
import com.javafxpert.carddeckdemo.deck.domain.Card;
import com.javafxpert.carddeckdemo.deck.repository.CardDeckRepository;
import com.javafxpert.carddeckdemo.deck.service.CardDeckService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class DefaultCardDeckService implements CardDeckService {

  private static final String DELIMITER = ",";
  private static final Comparator<Card> seqComparator = Comparator.comparing(Card::getId);

  private final CardDeckRepository cardDeckRepository;
  private final String             imagesUri;


  public DefaultCardDeckService(CardDeckImagesServerProperties cardDeckImagesServerProperties,
                         CardDeckRepository cardDeckRepository) {
    this.cardDeckRepository = cardDeckRepository;
    this.imagesUri = cardDeckImagesServerProperties.getURI("/images");
  }

  @Override
  public Flux<Card> parseString(Mono<String> deck) {
    return deck
            .map(c -> c.replaceAll(" ", ""))
            .filter(c -> c.length() >= 29)
            .as(this::createFluxFromCardsString)
            .switchIfEmpty(Flux.defer(this::generate));
  }

  @Override
  public Flux<Card> generate() {
    return cardDeckRepository.findAll().sort(seqComparator);
  }

  private Flux<Card> createFluxFromCardsString(Mono<String> cardStr) {
    return cardStr.flatMapMany(cs -> Flux.fromArray(cs.split(DELIMITER)))
                  .map(code -> new Card(code, imagesUri));
  }
}

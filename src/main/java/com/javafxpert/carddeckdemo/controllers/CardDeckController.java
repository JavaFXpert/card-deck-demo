package com.javafxpert.carddeckdemo.controllers;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.model.CardHand;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import com.javafxpert.carddeckdemo.util.ShuffleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cards/deck")
public class CardDeckController {
  private final CardDeckService cardDeckService;
  private final CardDeckDemoProperties cardDeckDemoProperties;

  @Autowired
  public CardDeckController(CardDeckService cardDeckService,
                            CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckService = cardDeckService;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
  }

  @GetMapping("/new")
  public Mono<CardHand> getCardDeck(@RequestParam(defaultValue = "52") int numcards) {
    return cardDeckService.getNewDeckFromDb()
        .take(numcards)
        .collectList()
        .map(l -> new CardHand(l, "New Deck"));
  }

  @GetMapping("/{suit}")
  public Mono<CardHand>  getCardDeckBySuit(
          @PathVariable String suit,
          @RequestParam(defaultValue = "13") int numcards
  ) {
    return cardDeckService.getNewDeck()
        .filter(card -> card.getSuit().equalsIgnoreCase(suit))
        .take(numcards)
        .collectList()
        .map(l -> new CardHand(l, "Only " + suit));
  }

  @GetMapping("/cut")
  public Mono<CardHand> getCardDeckCut(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
        .log()
        .map(c -> cards.replaceAll(" ", ""))
        .filter(c -> c.length() >= 29)
        .flatMapMany(cardDeckService::createFluxFromCardsString)
        .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
        .transform(ShuffleUtils::cutCards)
        .collectList()
        .map(l -> new CardHand(l, "Cut"));
  }

  @GetMapping("/overhandshuffle")
  public Mono<CardHand> getCardDeckOverhandShuffle(@RequestParam (defaultValue = "") String cards) {
	  return Mono.just(cards)
        .log()
        .map(c -> cards.replaceAll(" ", ""))
        .filter(c -> c.length() >= 29)
        .flatMapMany(cardDeckService::createFluxFromCardsString)
        .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
        .transform(ShuffleUtils::overhandShuffle)
        .collectList()
        .map(l -> new CardHand(l, "Overhand shuffle"));
  }

  @GetMapping("/riffleshuffle")
  public Mono<CardHand> getCardDeckRiffleShuffle(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
        .log()
        .map(c -> cards.replaceAll(" ", ""))
        .filter(c -> c.length() >= 29)
        .flatMapMany(cardDeckService::createFluxFromCardsString)
        .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
        .transform(ShuffleUtils::riffleShuffle)
        .collectList()
        .map(l -> new CardHand(l, "Riffle shuffle"));
  }

  @GetMapping("/dealpokerhand")
  public Mono<CardHand> getCardDeckDealPokerHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() >= 29)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(ShuffleUtils::dealPokerHand)
               .collectList()
               .flatMap(l -> retrievePokerHandName(Flux.fromIterable(l))
                   .map(handName -> new CardHand(l, handName)));

  }

  @GetMapping("/shuffledeal")
  public Mono<CardHand> getCardDeckShuffleDeal(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() >= 29)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(ShuffleUtils::shuffleWell)
               .transform(ShuffleUtils::dealPokerHand)
               .collectList()
               .flatMap(l -> retrievePokerHandName(Flux.fromIterable(l))
                                .map(handName -> new CardHand(l, handName)));
  }


  public Mono<String> retrievePokerHandName(Flux<Card> cardFlux) {
    Mono<String> cardsMonoString = cardDeckService.createStringFromCardFlux(cardFlux);
    String pokerScoreServiceUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport();
    WebClient pokerScoreWebClient = WebClient.create(pokerScoreServiceUri);
    return cardsMonoString.flatMap(cards ->
        pokerScoreWebClient.get()
            .uri("/cards/poker/identifyhand?cards=" + cards)
            .retrieve()
            .bodyToMono(String.class));
  }
}

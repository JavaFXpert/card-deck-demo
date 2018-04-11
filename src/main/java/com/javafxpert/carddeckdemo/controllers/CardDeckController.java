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
  public Flux<Card> getCardDeck(@RequestParam(defaultValue = "52") int numcards) {
    return cardDeckService.getNewDeck()
                          .take(numcards);
  }

  @GetMapping("/{suit}")
  public Flux<Card> getCardDeckBySuit(
          @PathVariable String suit,
          @RequestParam(defaultValue = "false") boolean shuffled,
          @RequestParam(defaultValue = "10") int numcards
  ) {

    return cardDeckService.getNewDeck()
                          .filter(card -> card.getSuit().equalsIgnoreCase(suit))
                          .take(numcards);
  }

  @GetMapping("/cut")
  public Flux<Card> getCardDeckCut(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() >= 29)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(ShuffleUtils::cutCards);
  }

  @GetMapping("/overhandshuffle")
  public Flux<Card> getCardDeckOverhandShuffle(@RequestParam (defaultValue = "") String cards) {
	  return Mono.just(cards)
	             .log()
	             .map(c -> cards.replaceAll(" ", ""))
	             .filter(c -> c.length() >= 29)
	             .flatMapMany(cardDeckService::createFluxFromCardsString)
	             .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
	             .transform(ShuffleUtils::overhandShuffle);
  }

  @GetMapping("/riffleshuffle")
  public Flux<Card> getCardDeckRiffleShuffle(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() >= 29)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(ShuffleUtils::riffleShuffle);
  }

  @GetMapping("/dealpokerhand")
  public Flux<Card> getCardDeckDealPokerHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() >= 29)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(ShuffleUtils::dealPokerHand);
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

    /*
    Project Reactor allows to collectFluxelements not only to List, but also to
    Map<K, T>withFlux.collectMap(); or to multimapMap<K, Collection<T>>withFlux.collectMultimap();
    or to any data structure with customjava.util.stream.Collector:Flux.collect(Collector).
     */
  }


  public Mono<String> retrievePokerHandName(Flux<Card> cardFlux) {
    Mono<String> cardsMonoString = cardDeckService.createStringFromCardFlux(cardFlux);
    String pokerScoreServiceUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport();
    WebClient pokerScoreWebClient = WebClient.create(pokerScoreServiceUri);
    return pokerScoreWebClient.get()
            .uri("/cards/poker/identifyhand?cards=" + cardsMonoString.block()) //TODO: Find alternative to block()?
        .retrieve()
        .bodyToMono(String.class); //TODO: Find alternative to block()?
  }
}

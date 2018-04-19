package com.javafxpert.carddeckdemo.deck.controller;

import java.util.Comparator;
import java.util.Optional;

import com.javafxpert.carddeckdemo.deck.domain.CardHand;
import com.javafxpert.carddeckdemo.deck.service.CardDeckService;
import com.javafxpert.carddeckdemo.deck.service.CardShufflingService;
import com.javafxpert.carddeckdemo.deck.service.PokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards/deck")
public class CardDeckController {
  private final CardDeckService      cardDeckService;
  private final PokerService         pokerService;
  private final CardShufflingService cardShufflingService;

  @Autowired
  public CardDeckController(
          CardDeckService cardDeckService,
          PokerService pokerService,
          CardShufflingService cardShufflingService) {
    this.cardDeckService = cardDeckService;
    this.pokerService = pokerService;
    this.cardShufflingService = cardShufflingService;
  }

  @GetMapping("/new")
  public Mono<CardHand> getCardDeck(@RequestParam(defaultValue = "52")
                                          int numcards) {
    return cardDeckService.generate()
                          .take(numcards)
                          .collectList()
                          .map(l -> new CardHand(l, "New Deck"));
  }

  @Bean
  RouterFunction<ServerResponse> newDeckRoutes(CardDeckService cds) {
    int defaultNumCards = 52;
    return RouterFunctions.route(
        RequestPredicates.GET("/newdeck"),
        request -> ServerResponse
            .ok()
            .body(cds.generate()
                    .take(request.queryParam("numcards")
                    .map(s -> Integer.parseInt(s)).orElse(defaultNumCards))
                    .collectList()
                    .map(l -> new CardHand(l,"New Deck")),
                CardHand.class));
  }

  @GetMapping("/{suit}")
  public Mono<CardHand>  getCardDeckBySuit(
          @PathVariable String suit,
          @RequestParam(defaultValue = "10") int numcards
  ) {
    return cardDeckService.generate()
                          .filter(card -> card.getSuit()
                                              .equalsIgnoreCase(suit))
                          .take(numcards)
                          .collectList()
                          .map(l -> new CardHand(l, "Only " + suit));
  }

  @GetMapping("/cut")
  public Mono<CardHand> getCardDeckCut(@RequestParam(defaultValue = "") String cards) {
    return Mono.just(cards)
               .as(cardDeckService::parseString)
               .transform(cardShufflingService::cutCards)
               .collectList()
               .map(l -> new CardHand(l, "Cut Cards"));
  }

  @GetMapping("/overhandshuffle")
  public Mono<CardHand> getCardDeckOverhandShuffle(@RequestParam(defaultValue = "") String cards) {
	  return Mono.just(cards)
                 .as(cardDeckService::parseString)
                 .transform(cardShufflingService::overhandShuffle)
                 .collectList()
                 .map(l -> new CardHand(l, "Overhand Shuffle"));
  }

  @GetMapping("/riffleshuffle")
  public Mono<CardHand> getCardDeckRiffleShuffle(@RequestParam(defaultValue = "") String cards) {
    return Mono.just(cards)
               .as(cardDeckService::parseString)
               .transform(cardShufflingService::riffleShuffle)
               .collectList()
               .map(l -> new CardHand(l, "Riffle Shuffle"));
  }

  @GetMapping("/randomshuffle")
  public Mono<CardHand> getCardDeckRandomShuffle(@RequestParam(defaultValue = "") String cards) {
    return Mono.just(cards)
               .as(cardDeckService::parseString)
               .transform(cardShufflingService::randomShuffle)
               .collectList()
               .map(l -> new CardHand(l, "Random Shuffle"));
  }

  @GetMapping("/dealpokerhand")
  public Mono<CardHand> getCardDeckDealPokerHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .as(cardDeckService::parseString)
               .transform(cardShufflingService::dealPokerHand)
               .collectList()
               .flatMap(l -> pokerService.handNameFromDeck(Flux.fromIterable(l))
                                         .map(handName -> new CardHand(l, handName)));

  }

  @GetMapping("/shuffledeal")
  public Mono<CardHand> getCardDeckShuffleDeal(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .as(cardDeckService::parseString)
               .transform(cardShufflingService::shuffleWell)
               .transform(cardShufflingService::dealPokerHand)
               .collectList()
               .flatMap(l -> pokerService.handNameFromDeck(Flux.fromIterable(l))
                                         .map(handName -> new CardHand(l, handName)));
  }

  @GetMapping("/shuffledealrepeat")
  public Flux<Tuple3<String, Long, Double>> shuffleDealRepeatCollectStats(@RequestParam (defaultValue = "10") int numtimes) {
    Comparator<Tuple3<String, Long, Double>> t3Comparator = Comparator.comparingLong(Tuple2::getT2);
    return Flux
        .range(0, numtimes)
        .flatMap(i ->
            Flux.defer(cardDeckService::generate)
                .compose(cardShufflingService::shuffleWell)
                .compose(cardShufflingService::dealPokerHand)
                .subscribeOn(Schedulers.parallel())
                .collectList()
                .flatMap(l -> pokerService.handNameFromDeck(Flux.fromIterable(l))
                                          .map(handName -> new CardHand(l, handName)))
        )
        .groupBy(CardHand::getName)
        .flatMap(gf -> gf.count().map(c -> Tuples.of(gf.key(), c, Math.round(c  * 10000.0 / numtimes) / 100.0)))
        .sort(t3Comparator);
  }
}

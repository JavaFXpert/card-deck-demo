package com.javafxpert.carddeckdemo.poker.controller;

import com.javafxpert.carddeckdemo.deck.domain.Card;
import com.javafxpert.carddeckdemo.deck.service.impl.DefaultCardDeckService;
import com.javafxpert.carddeckdemo.poker.service.PokerService;
import com.javafxpert.carddeckdemo.poker.domain.ScoreHand;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RestController
@RequestMapping("/poker")
public class PokerController {
  private final PokerService pokerService;

  public PokerController(PokerService pokerService) {
    this.pokerService = pokerService;
  }

  @PostMapping("/hand")
  public Mono<ResponseEntity<String>> identifyHand(Flux<Card> cardFlux) {
    return cardFlux.map(Card::getSeq)
                   .collectList()
                   .filter(l -> l.size() == 5)
                   .map(list -> new ScoreHand(list).getRank().getName())
                   .flatMap(handName ->
                       pokerService.updateHandFrequency(Mono.just(handName))
                                   .then(Mono.just(ResponseEntity.ok(handName)))
                   )
                   .defaultIfEmpty(ResponseEntity.badRequest().body("Illegal cards amount"));
  }

  @Bean
  RouterFunction<ServerResponse> routes(DefaultCardDeckService cardDeckService) {
    return RouterFunctions.route(
      RequestPredicates.POST("/poker/idhand"), request ->
        request.bodyToFlux(Card.class)
               .map(Card::getSeq)
               .collectList()
               .filter(l -> l.size() == 5)
               .map(list -> new ScoreHand(list).getRank().getName())
               .flatMap(handName ->
                   pokerService.updateHandFrequency(Mono.just(handName))
                               .then(ServerResponse.ok().syncBody(handName))
               )
               .switchIfEmpty(ServerResponse.badRequest().syncBody("Illegal cards amount"))
    );
  }
}

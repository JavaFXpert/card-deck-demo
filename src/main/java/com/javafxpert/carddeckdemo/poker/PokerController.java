package com.javafxpert.carddeckdemo.poker;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.carddeck.CardDeckService;
import com.javafxpert.carddeckdemo.poker.ScoreHand;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cards/poker")
public class PokerController {
  private final CardDeckService cardDeckService;
  private final PokerService pokerService;
  private final CardDeckDemoProperties cardDeckDemoProperties;

  public PokerController(CardDeckService cardDeckService, PokerService pokerService, CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckService = cardDeckService;
    this.pokerService = pokerService;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
  }

  //TODO: Add switchIfEmpty to protect case in which too many or too little cards are passed in
  @GetMapping("/identifyhand")
  public Mono<String> identifyHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
      .log()
      .map(c -> cards.replaceAll(" ", ""))
      .filter(c -> c.length() == 14)  // Five card codes at two characters each, plus 4 commas
      .flatMapMany(cardDeckService::createFluxFromCardsString)
      .map(card -> card.getSeq())
      .collectList()
      .flatMap(list -> {
        ScoreHand scoreHand = new ScoreHand(list);
        String handName = scoreHand.getRank().getName();
        return Mono.just(handName);
      });
  }


  @Bean
  RouterFunction<ServerResponse> routes(CardDeckService cardDeckService) {
    return RouterFunctions.route(
      RequestPredicates.GET("/cards/poker/idhand"), request -> ServerResponse
        .ok()
        .body(Mono.just(request.uri().getQuery().substring(6))
            .log()
            .map(c -> request.uri().getQuery().substring(6).replaceAll(" ", ""))
            .filter(c -> c.length() == 14)  // Five card codes at two characters each, plus 4 commas
            .flatMapMany(cardDeckService::createFluxFromCardsString)
            .map(card -> card.getSeq())
            .collectList()
            .flatMap(list -> {
              ScoreHand scoreHand = new ScoreHand(list);
              String handName = scoreHand.getRank().getName();
              Mono<String> handNameMono = Mono.just(handName);
              pokerService.updateHandFrequency(handNameMono);
              return handNameMono;
            }), String.class));


  }
}
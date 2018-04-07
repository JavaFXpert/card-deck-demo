package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

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

  @GetMapping("/")
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
               .filter(c -> c.length() < 30)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(cardDeckService::cutCards);
  }

  @GetMapping("/overhandshuffle")
  public Flux<Card> getCardDeckOverhandShuffle(@RequestParam (defaultValue = "") String cards) {
	  return Mono.just(cards)
	             .log()
	             .map(c -> cards.replaceAll(" ", ""))
	             .filter(c -> c.length() < 30)
	             .flatMapMany(cardDeckService::createFluxFromCardsString)
	             .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
	             .transform(cardDeckService::overhandShuffle);
  }

  @GetMapping("/riffleshuffle")
  public Flux<Card> getCardDeckRiffleShuffle(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() < 30)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(cardDeckService::riffleShuffle);
  }

  @GetMapping("/dealpokerhand")
  public Flux<Card> getCardDeckDealPokerHand(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() < 30)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(cardDeckService::dealPokerHand);
  }

  @GetMapping("/carddeckshuffledeal")
  public Flux<Card> getCardDeckShuffleDeal(@RequestParam (defaultValue = "") String cards) {
    return Mono.just(cards)
               .log()
               .map(c -> cards.replaceAll(" ", ""))
               .filter(c -> c.length() < 30)
               .flatMapMany(cardDeckService::createFluxFromCardsString)
               .switchIfEmpty(Flux.defer(cardDeckService::getNewDeck))
               .transform(cardDeckService::riffleShuffle)
               .transform(cardDeckService::dealPokerHand);
  }


  /*
  @GetMapping("/carddeckwebclient")
  public Flux<Card> getCardDeck(boolean shuffled, int numcards) {
    String cardDeckServiceUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/carddeck";
    WebClient cardDeckWebClient = WebClient.create(cardDeckServiceUri);
    Flux<Card> cardFlux = cardDeckWebClient.get()
            .uri(builder -> builder.queryParam("shuffled", shuffled).queryParam("request", numcards).build())
            .
    return cardDeckService.getNewDeck(shuffled);


    CardDeckSubscriber<Card> cardDeckSubscriber = new CardDeckSubscriber<>();
    cardFlux.subscribe(cardDeckSubscriber);
    System.out.println("Requesting 3 more");
    cardDeckSubscriber.request(3);

  }
  */

}

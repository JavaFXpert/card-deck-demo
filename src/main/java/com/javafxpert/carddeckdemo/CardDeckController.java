package com.javafxpert.carddeckdemo;

import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.services.CardDeckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Comparator;

@RestController
public class CardDeckController {
  private final CardDeckService cardDeckService;
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private final Comparator<Card> comparator = (c1, c2) -> c1.getWorth() - c2.getWorth();

  @Autowired
  public CardDeckController(CardDeckService cardDeckService,
                            CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckService = cardDeckService;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
  }

  @GetMapping("/carddeck")
  public Flux<Card> getCardDeck(@RequestParam(defaultValue = "false") boolean shuffled, @RequestParam(defaultValue = "10") int numcards) {

    Flux<Card> cardFlux = cardDeckService.getAllCards(shuffled)
            .take(numcards);
    return cardFlux;
  }

  @GetMapping("/carddeckbysuit")
  public Flux<Card> getCardDeckBySuit(@RequestParam(defaultValue = "SPADES") String suit, @RequestParam(defaultValue = "false") boolean shuffled, @RequestParam(defaultValue = "10") int numcards) {

    Flux<Card> cardFlux = cardDeckService.getAllCards(shuffled)
            .filter(card -> card.getSuit().equalsIgnoreCase(suit))
            .take(numcards);
    return cardFlux;
  }

  @GetMapping("/carddeckmerge")
  public Flux<Card> getCardDeckMerge() {
    Flux<Card> clubsFlux = getCardDeckBySuit("CLUBS", false, 13)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> heartsFlux = getCardDeckBySuit("HEARTS", false, 13)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> spadesFlux = getCardDeckBySuit("SPADES", false, 13)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> diamondsFlux = getCardDeckBySuit("DIAMONDS", false, 13)
            .delayElements(Duration.ofMillis(1));

    Flux<Card> cardFlux = Flux.merge(heartsFlux, clubsFlux, spadesFlux, diamondsFlux)
            .take(12);

    return cardFlux;
  }

  @GetMapping("/carddeckmergewith")
  public Flux<Card> getCardDeckMergeWith() {
    Flux<Card> clubsFlux = getCardDeckBySuit("CLUBS", false, 6)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> heartsFlux = getCardDeckBySuit("HEARTS", false, 6)
            .delayElements(Duration.ofMillis(1));

    Flux<Card> cardFlux = heartsFlux.mergeWith(clubsFlux).take(12);

    return cardFlux;
  }
  @GetMapping("/carddeckmergeordered")
  public Flux<Card> getCardDeckMergeOrdered() {
    Flux<Card> clubsFlux = getCardDeckBySuit("CLUBS", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> heartsFlux = getCardDeckBySuit("HEARTS", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> spadesFlux = getCardDeckBySuit("SPADES", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> diamondsFlux = getCardDeckBySuit("DIAMONDS", true, 3)
            .delayElements(Duration.ofMillis(1));

    Flux<Card> cardFlux = Flux.mergeOrdered(comparator, heartsFlux, clubsFlux, spadesFlux, diamondsFlux).take(12);

    return cardFlux;
  }

  @GetMapping("/carddeckmergesort")
  public Flux<Card> getCardDeckMergeSort() {
    Flux<Card> clubsFlux = getCardDeckBySuit("CLUBS", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> heartsFlux = getCardDeckBySuit("HEARTS", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> spadesFlux = getCardDeckBySuit("SPADES", true, 3)
            .delayElements(Duration.ofMillis(1));
    Flux<Card> diamondsFlux = getCardDeckBySuit("DIAMONDS", true, 3)
            .delayElements(Duration.ofMillis(1));

    Flux<Card> cardFlux = Flux.merge(heartsFlux, clubsFlux, spadesFlux, diamondsFlux)
            .sort(comparator)
            .take(12);

    return cardFlux;
  }

  @GetMapping("/carddecktakelast")
  public Flux<Card> getCardDeckTakeLast() {
    Flux<Card> clubsFlux = getCardDeckBySuit("CLUBS", true, 13).sort(comparator).takeLast(3);
    Flux<Card> heartsFlux = getCardDeckBySuit("HEARTS", true, 13).sort(comparator).takeLast(3);
    Flux<Card> spadesFlux = getCardDeckBySuit("SPADES", true, 13).sort(comparator).takeLast(3);
    Flux<Card> diamondsFlux = getCardDeckBySuit("DIAMONDS", true, 13).sort(comparator).takeLast(3);

    Flux<Card> cardFlux = Flux.merge(heartsFlux, clubsFlux, spadesFlux, diamondsFlux)
            .take(12);

    return cardFlux;
  }

  /*
  @GetMapping("/carddeckwebclient")
  public Flux<Card> getCardDeck(boolean shuffled, int numcards) {
    String cardDeckServiceUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/carddeck";
    WebClient cardDeckWebClient = WebClient.create(cardDeckServiceUri);
    Flux<Card> cardFlux = cardDeckWebClient.get()
            .uri(builder -> builder.queryParam("shuffled", shuffled).queryParam("request", numcards).build())
            .
    return cardDeckService.getAllCards(shuffled);


    CardDeckSubscriber<Card> cardDeckSubscriber = new CardDeckSubscriber<>();
    cardFlux.subscribe(cardDeckSubscriber);
    System.out.println("Requesting 3 more");
    cardDeckSubscriber.request(3);

  }
  */

}

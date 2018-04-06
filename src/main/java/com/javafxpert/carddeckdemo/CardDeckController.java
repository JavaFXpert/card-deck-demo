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
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;

@RestController
public class CardDeckController {
  private final CardDeckService cardDeckService;
  private final CardDeckDemoProperties cardDeckDemoProperties;

  @Autowired
  public CardDeckController(CardDeckService cardDeckService,
                            CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckService = cardDeckService;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
  }

  @GetMapping("/carddeck")
  public Flux<Card> getCardDeck(@RequestParam(defaultValue = "52") int numcards) {

    Flux<Card> cardFlux = cardDeckService.getNewDeck()
            .take(numcards);
    return cardFlux;
  }

  @GetMapping("/carddeckbysuit")
  public Flux<Card> getCardDeckBySuit(@RequestParam(defaultValue = "SPADES") String suit, @RequestParam(defaultValue = "false") boolean shuffled, @RequestParam(defaultValue = "10") int numcards) {

    Flux<Card> cardFlux = cardDeckService.getNewDeck()
            .filter(card -> card.getSuit().equalsIgnoreCase(suit))
            .take(numcards);
    return cardFlux;
  }

  @GetMapping("/carddeckcut")
  public Flux<Card> getCardDeckCut(@RequestParam (defaultValue = "") String cards) {
    System.out.println("cards: " + cards);
    String cardStr = cards.replaceAll(" ", "");
    Flux<Card> cardFlux;

    if (cardStr.length() < 30) { // if there are less than 10 cards, get a new deck
      cardFlux = cardDeckService.getNewDeck();
    }
    else {
      cardFlux = cardDeckService.createFluxFromCardsString(cardStr);
    }

    return cardDeckService.cutCards(cardFlux);
  }

  @GetMapping("/carddeckoverhandshuffle")
  public Flux<Card> getCardDeckOverhandShuffle(@RequestParam (defaultValue = "") String cards) {
    System.out.println("cards: " + cards);
    String cardStr = cards.replaceAll(" ", "");
    Flux<Card> cardFlux;

    if (cardStr.length() < 30) { // if there are less than 10 cards, get a new deck
      cardFlux = cardDeckService.getNewDeck();
    }
    else {
      cardFlux = cardDeckService.createFluxFromCardsString(cardStr);
    }

    return cardDeckService.overhandShuffle(cardFlux);
  }

  @GetMapping("/carddeckriffleshuffle")
  public Flux<Card> getCardDeckRiffleShuffle(@RequestParam (defaultValue = "") String cards) {
    System.out.println("cards: " + cards);
    String cardStr = cards.replaceAll(" ", "");
    Flux<Card> cardFlux;

    if (cardStr.length() < 30) { // if there are less than 10 cards, get a new deck
      cardFlux = cardDeckService.getNewDeck();
    }
    else {
      cardFlux = cardDeckService.createFluxFromCardsString(cardStr);
    }

    return cardDeckService.riffleShuffle(cardFlux);
  }

  @GetMapping("/carddeckdealpokerhand")
  public Flux<Card> getCardDeckDealPokerHand(@RequestParam (defaultValue = "") String cards) {
    System.out.println("cards: " + cards);
    String cardStr = cards.replaceAll(" ", "");
    Flux<Card> cardFlux;

    if (cardStr.length() < 30) { // if there are less than 10 cards, get a new deck
      cardFlux = cardDeckService.getNewDeck();
    }
    else {
      cardFlux = cardDeckService.createFluxFromCardsString(cardStr);
    }

    return cardDeckService.dealPokerHand(cardFlux);
  }

  @GetMapping("/carddeckshuffledeal")
  public Flux<Card> getCardDeckShuffleDeal(@RequestParam (defaultValue = "") String cards) {
    System.out.println("cards: " + cards);
    String cardStr = cards.replaceAll(" ", "");
    Flux<Card> cardFlux;

    if (cardStr.length() < 30) { // if there are less than 10 cards, get a new deck
      cardFlux = cardDeckService.getNewDeck();
    }
    else {
      cardFlux = cardDeckService.createFluxFromCardsString(cardStr);
    }

    //TODO: Make more Fluxy
    return cardDeckService.dealPokerHand(cardDeckService.shuffleWell(cardFlux));
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

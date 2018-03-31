package com.javafxpert.carddeckdemo.services;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.model.Card;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CardDeckService {
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private static List<Card> cardList = new ArrayList<>();
  private String imagesUri = "";

  public CardDeckService(CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckDemoProperties = cardDeckDemoProperties;
    imagesUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/images";
  }

  public Flux<Card> getAllCards(boolean shuffled) {
    List<Card> retList;
    if (cardList != null || cardList.isEmpty()) {
      cardList = createCards();
    }

    if (shuffled) {
      retList = new ArrayList<>(cardList);
      Collections.shuffle(retList);
    }
    else {
      retList = cardList;
    }
    return Flux.<Card>fromIterable(retList)
      .delayElements(Duration.ofMillis(0));
  }

  /*
   * Populate the list of cards
   * TODO: Consider using loops instead of being so verbose
   */
  private List<Card> createCards() {
    List<Card> cards = new ArrayList<>();
    cards.add(new Card("AS", imagesUri));
    cards.add(new Card("2S", imagesUri));
    cards.add(new Card("3S", imagesUri));
    cards.add(new Card("4S", imagesUri));
    cards.add(new Card("5S", imagesUri));
    cards.add(new Card("6S", imagesUri));
    cards.add(new Card("7S", imagesUri));
    cards.add(new Card("8S", imagesUri));
    cards.add(new Card("9S", imagesUri));
    cards.add(new Card("0S", imagesUri));
    cards.add(new Card("JS", imagesUri));
    cards.add(new Card("QS", imagesUri));
    cards.add(new Card("KS", imagesUri));

    cards.add(new Card("AD", imagesUri));
    cards.add(new Card("2D", imagesUri));
    cards.add(new Card("3D", imagesUri));
    cards.add(new Card("4D", imagesUri));
    cards.add(new Card("5D", imagesUri));
    cards.add(new Card("6D", imagesUri));
    cards.add(new Card("7D", imagesUri));
    cards.add(new Card("8D", imagesUri));
    cards.add(new Card("9D", imagesUri));
    cards.add(new Card("0D", imagesUri));
    cards.add(new Card("JD", imagesUri));
    cards.add(new Card("QD", imagesUri));
    cards.add(new Card("KD", imagesUri));

    cards.add(new Card("AC", imagesUri));
    cards.add(new Card("2C", imagesUri));
    cards.add(new Card("3C", imagesUri));
    cards.add(new Card("4C", imagesUri));
    cards.add(new Card("5C", imagesUri));
    cards.add(new Card("6C", imagesUri));
    cards.add(new Card("7C", imagesUri));
    cards.add(new Card("8C", imagesUri));
    cards.add(new Card("9C", imagesUri));
    cards.add(new Card("0C", imagesUri));
    cards.add(new Card("JC", imagesUri));
    cards.add(new Card("QC", imagesUri));
    cards.add(new Card("KC", imagesUri));

    cards.add(new Card("AH", imagesUri));
    cards.add(new Card("2H", imagesUri));
    cards.add(new Card("3H", imagesUri));
    cards.add(new Card("4H", imagesUri));
    cards.add(new Card("5H", imagesUri));
    cards.add(new Card("6H", imagesUri));
    cards.add(new Card("7H", imagesUri));
    cards.add(new Card("8H", imagesUri));
    cards.add(new Card("9H", imagesUri));
    cards.add(new Card("0H", imagesUri));
    cards.add(new Card("JH", imagesUri));
    cards.add(new Card("QH", imagesUri));
    cards.add(new Card("KH", imagesUri));

    return cards;
  }

}

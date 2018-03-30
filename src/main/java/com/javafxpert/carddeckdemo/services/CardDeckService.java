package com.javafxpert.carddeckdemo.services;

import com.javafxpert.carddeckdemo.model.Card;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardDeckService {
  private static List<Card> cardList = new ArrayList<>();

  public Flux<Card> getAllCards(boolean shuffled) {
    if (cardList != null || cardList.isEmpty()) {
      cardList = createCards();
    }
    return Flux.<Card>fromIterable(cardList);
  }

  /*
   * Populate the list of cards
   * TODO: Consider using loops instead of being so verbose
   */
  private static List<Card> createCards() {
    List<Card> cards = new ArrayList<>();
    cards.add(new Card("AS"));
    cards.add(new Card("2S"));
    cards.add(new Card("3S"));
    cards.add(new Card("4S"));
    cards.add(new Card("5S"));
    cards.add(new Card("6S"));
    cards.add(new Card("7S"));
    cards.add(new Card("8S"));
    cards.add(new Card("9S"));
    cards.add(new Card("0S"));
    cards.add(new Card("JS"));
    cards.add(new Card("QS"));
    cards.add(new Card("KS"));

    cards.add(new Card("AD"));
    cards.add(new Card("2D"));
    cards.add(new Card("3D"));
    cards.add(new Card("4D"));
    cards.add(new Card("5D"));
    cards.add(new Card("6D"));
    cards.add(new Card("7D"));
    cards.add(new Card("8D"));
    cards.add(new Card("9D"));
    cards.add(new Card("0D"));
    cards.add(new Card("JD"));
    cards.add(new Card("QD"));
    cards.add(new Card("KD"));

    cards.add(new Card("AC"));
    cards.add(new Card("2C"));
    cards.add(new Card("3C"));
    cards.add(new Card("4C"));
    cards.add(new Card("5C"));
    cards.add(new Card("6C"));
    cards.add(new Card("7C"));
    cards.add(new Card("8C"));
    cards.add(new Card("9C"));
    cards.add(new Card("0C"));
    cards.add(new Card("JC"));
    cards.add(new Card("QC"));
    cards.add(new Card("KC"));

    cards.add(new Card("AH"));
    cards.add(new Card("2H"));
    cards.add(new Card("3H"));
    cards.add(new Card("4H"));
    cards.add(new Card("5H"));
    cards.add(new Card("6H"));
    cards.add(new Card("7H"));
    cards.add(new Card("8H"));
    cards.add(new Card("9H"));
    cards.add(new Card("0H"));
    cards.add(new Card("JH"));
    cards.add(new Card("QH"));
    cards.add(new Card("KH"));

    return cards;
  }

}

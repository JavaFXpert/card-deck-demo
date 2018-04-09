/*
 Card images from http://acbl.mybigcommerce.com/52-playing-cards/
 Card data model inspired by Chase Robert's elegant Deck of Cards API https://deckofcardsapi.com/
 */
package com.javafxpert.carddeckdemo.model;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Objects;

public class Card implements Comparable {
  private String code;
  private String value;
  private String suit;
  private String image;
  private int worth;
  private int seq;

  public Card(String code, String imagesUri) {
    this.code = code;
    String valueCode = code.substring(0,1);
    switch (valueCode) {
      case "A":
        value = "ACE";
        worth = 0;
        break;
      case "2":
        value = "2";
        worth = 1;
        break;
      case "3":
        value = "3";
        worth = 2;
        break;
      case "4":
        value = "4";
        worth = 3;
        break;
      case "5":
        value = "5";
        worth = 4;
        break;
      case "6":
        value = "6";
        worth = 5;
        break;
      case "7":
        value = "7";
        worth = 6;
        break;
      case "8":
        value = "8";
        worth = 7;
        break;
      case "9":
        value = "9";
        worth = 8;
        break;
      case "0":
        value = "10";
        worth = 9;
        break;
      case "J":
        value = "JACK";
        worth = 10;
        break;
      case "Q":
        value = "QUEEN";
        worth = 11;
        break;
      case "K":
        value = "KING";
        worth = 12;
    }

    String suitCode = code.substring(1,2);
    switch (suitCode) {
      case "C":
        suit = "CLUBS";
        seq = worth + (13 * ((12 - worth + 0) % 4));
        break;
      case "H":
        suit = "HEARTS";
        seq = worth + (13 * ((12 - worth + 1) % 4));
        break;
      case "S":
        suit = "SPADES";
        seq = worth + (13 * ((12 - worth + 2) % 4));
        break;
      case "D":
        suit = "DIAMONDS";
        seq = worth + (13 * ((12 - worth + 3) % 4));
    }

    image = imagesUri + "/" + code + ".png";
  }

  public String getCode() {
    return code;
  }

  public String getValue() {
    return value;
  }

  public String getSuit() {
    return suit;
  }

  public String getImage() {
    return image;
  }

  public int getWorth() {
    return worth;
  }

  public int getSeq() {
    return seq;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Card card = (Card) o;
    return Objects.equals(code, card.code) &&
            Objects.equals(value, card.value) &&
            Objects.equals(suit, card.suit) &&
            Objects.equals(image, card.image);
  }

  @Override
  public int hashCode() {

    return Objects.hash(code, value, suit, image);
  }

  @Override
  public int compareTo(Object o) {
    return ((Card)o).getWorth();
  }

  @Override
  public String toString() {
    return "Card{" +
            "code='" + code + '\'' +
            ", value='" + value + '\'' +
            ", suit='" + suit + '\'' +
            ", image='" + image + '\'' +
            ", worth=" + worth +
            '}';
  }
}

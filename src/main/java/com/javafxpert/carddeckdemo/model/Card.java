/*
 Card images from http://acbl.mybigcommerce.com/52-playing-cards/
 Card data model inspired by Chase Robert's elegant Deck of Cards API https://deckofcardsapi.com/
 */
package com.javafxpert.carddeckdemo.model;

import java.util.Objects;

public class Card {
  private String code;
  private String value;
  private String suit;
  private String image;

  public Card(String code) {
    this.code = code;
    String valueCode = code.substring(0,1);
    switch (valueCode) {
      case "A":
        value = "ACE";
        break;
      case "2":
        value = "2";
        break;
      case "3":
        value = "3";
        break;
      case "4":
        value = "4";
        break;
      case "5":
        value = "5";
        break;
      case "6":
        value = "6";
        break;
      case "7":
        value = "7";
        break;
      case "8":
        value = "8";
        break;
      case "9":
        value = "9";
        break;
      case "0":
        value = "10";
        break;
      case "J":
        value = "JACK";
        break;
      case "Q":
        value = "QUEEN";
        break;
      case "K":
        value = "KING";
    }

    String suitCode = code.substring(1,2);
    switch (suitCode) {
      case "S":
        suit = "SPADES";
        break;
      case "D":
        suit = "DIAMONDS";
        break;
      case "C":
        suit = "CLUBS";
        break;
      case "H":
        suit = "HEARTS";
    }

    image = "/static/images/" + code + ".png";
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
  public String toString() {
    return "Card{" +
      "code='" + code + '\'' +
      ", value='" + value + '\'' +
      ", suit='" + suit + '\'' +
      ", image='" + image + '\'' +
      '}';
  }
}

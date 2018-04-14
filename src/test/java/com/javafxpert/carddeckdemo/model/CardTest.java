package com.javafxpert.carddeckdemo.model;

import com.javafxpert.carddeckdemo.carddeck.Card;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
  Card cardAS = new Card("AS" , "");
  Card card2S = new Card("2S" , "");
  Card card3S = new Card("3S" , "");
  Card card4S = new Card("4S" , "");
  Card card5S = new Card("5S" , "");
  Card card6S = new Card("6S" , "");
  Card card7S = new Card("7S" , "");
  Card card8S = new Card("8S" , "");
  Card card9S = new Card("9S" , "");
  Card card0S = new Card("0S" , "");
  Card cardJS = new Card("JS" , "");
  Card cardQS = new Card("QS" , "");
  Card cardKS = new Card("KS" , "");

  Card cardAD = new Card("AD" , "");

  Card cardAC = new Card("AC" , "");

  Card cardAH = new Card("AH" , "");

  @org.junit.jupiter.api.Test
  void getCode() {
    assertEquals("AS", cardAS.getCode());
  }

  @org.junit.jupiter.api.Test
  void getValue() {
    assertEquals("ACE", cardAS.getValue());
    assertEquals("2", card2S.getValue());
    assertEquals("3", card3S.getValue());
    assertEquals("4", card4S.getValue());
    assertEquals("5", card5S.getValue());
    assertEquals("6", card6S.getValue());
    assertEquals("7", card7S.getValue());
    assertEquals("8", card8S.getValue());
    assertEquals("9", card9S.getValue());
    assertEquals("10", card0S.getValue());
    assertEquals("JACK", cardJS.getValue());
    assertEquals("QUEEN", cardQS.getValue());
    assertEquals("KING", cardKS.getValue());
  }

  @org.junit.jupiter.api.Test
  void getSuit() {
    assertEquals("SPADES", cardAS.getSuit());
    assertEquals("DIAMONDS", cardAD.getSuit());
    assertEquals("CLUBS", cardAC.getSuit());
    assertEquals("HEARTS", cardAH.getSuit());
  }

  @org.junit.jupiter.api.Test
  void getImage() {
    assertEquals("/AS.png", cardAS.getImage());
  }
}

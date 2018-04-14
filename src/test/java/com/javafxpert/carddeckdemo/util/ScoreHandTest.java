package com.javafxpert.carddeckdemo.util;

import com.javafxpert.carddeckdemo.carddeck.Card;
import com.javafxpert.carddeckdemo.poker.Cards;
import com.javafxpert.carddeckdemo.poker.PokerHand;
import com.javafxpert.carddeckdemo.poker.ScoreHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreHandTest {
  Cards cards = new Cards();

  @Test
  void aceDiamondsTest() {
    int aceDiamondsSeq = new Card("AD", "").getSeq();
    assertEquals("Ace of Diamonds", cards.getCardName(aceDiamondsSeq));
  }

  @Test
  void aceClubsTest() {
    int aceClubsSeq = new Card("AC", "").getSeq();
    assertEquals("Ace of Clubs", cards.getCardName(aceClubsSeq));
  }

  @Test
  void twoDiamondsTest() {
    int twoDiamondsSeq = new Card("2D", "").getSeq();
    assertEquals("Two of Diamonds", cards.getCardName(twoDiamondsSeq));
  }

  @Test
  void twoClubsTest() {
    int twoClubsSeq = new Card("2C", "").getSeq();
    assertEquals("Two of Clubs", cards.getCardName(twoClubsSeq));
  }

  @Test
  void twoHeartsTest() {
    int twoHeartsSeq = new Card("2H", "").getSeq();
    assertEquals("Two of Hearts", cards.getCardName(twoHeartsSeq));
  }

  @Test
  void twoSpadesTest() {
    int twoSpadesSeq = new Card("2S", "").getSeq();
    assertEquals("Two of Spades", cards.getCardName(twoSpadesSeq));
  }

  @Test
  void threeDiamondsTest() {
    int threeDiamondsSeq = new Card("3D", "").getSeq();
    assertEquals("Three of Diamonds", cards.getCardName(threeDiamondsSeq));
  }

  @Test
  void threeClubsTest() {
    int threeClubsSeq = new Card("3C", "").getSeq();
    assertEquals("Three of Clubs", cards.getCardName(threeClubsSeq));
  }

  @Test
  void threeHeartsTest() {
    int threeHeartsSeq = new Card("3H", "").getSeq();
    assertEquals("Three of Hearts", cards.getCardName(threeHeartsSeq));
  }

  @Test
  void threeSpadesTest() {
    int threeSpadesSeq = new Card("3S", "").getSeq();
    assertEquals("Three of Spades", cards.getCardName(threeSpadesSeq));
  }


  @Test
  void KingSpadesTest() {
    int kingSpadesSeq = new Card("KS", "").getSeq();
    assertEquals("King of Spades", cards.getCardName(kingSpadesSeq));
  }

  @Test
  void highCardTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("QD", "").getSeq(),
        new Card("4C", "").getSeq(),
        new Card("2H", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("3D", "").getSeq())
    );
    assertEquals(PokerHand.HIGH_CARD, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void onePairTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("2S", "").getSeq(),
        new Card("4C", "").getSeq(),
        new Card("3H", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("3D", "").getSeq())
    );
    assertEquals(PokerHand.ONE_PAIR, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void twoPairTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("KH", "").getSeq(),
        new Card("4C", "").getSeq(),
        new Card("3H", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("3D", "").getSeq())
    );
    assertEquals(PokerHand.TWO_PAIR, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void threeOfAKindTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("KC", "").getSeq(),
        new Card("4C", "").getSeq(),
        new Card("KH", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("3D", "").getSeq())
    );
    assertEquals(PokerHand.THREE_OF_A_KIND, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void fourOfAKindTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("KC", "").getSeq(),
        new Card("4C", "").getSeq(),
        new Card("KH", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("KD", "").getSeq())
    );
    assertEquals(PokerHand.FOUR_OF_A_KIND, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void fullHouseTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("KH", "").getSeq(),
        new Card("3C", "").getSeq(),
        new Card("3H", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("3D", "").getSeq())
    );
    assertEquals(PokerHand.FULL_HOUSE, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void straightTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("2C", "").getSeq(),
        new Card("3C", "").getSeq(),
        new Card("4H", "").getSeq(),
        new Card("5S", "").getSeq(),
        new Card("6D", "").getSeq())
    );
    assertEquals(PokerHand.STRAIGHT, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void straightLowAceTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("AC", "").getSeq(),
        new Card("2C", "").getSeq(),
        new Card("3H", "").getSeq(),
        new Card("4S", "").getSeq(),
        new Card("5D", "").getSeq())
    );
    assertEquals(PokerHand.STRAIGHT, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void straightHighAceTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("0C", "").getSeq(),
        new Card("JC", "").getSeq(),
        new Card("QH", "").getSeq(),
        new Card("KS", "").getSeq(),
        new Card("AD", "").getSeq())
    );
    assertEquals(PokerHand.ROYAL_STRAIGHT, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void straightFlushTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("2D", "").getSeq(),
        new Card("3D", "").getSeq(),
        new Card("4D", "").getSeq(),
        new Card("5D", "").getSeq(),
        new Card("6D", "").getSeq())
    );
    assertEquals(PokerHand.STRAIGHT_FLUSH, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void royalStraightTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("0C", "").getSeq(),
        new Card("JC", "").getSeq(),
        new Card("QD", "").getSeq(),
        new Card("KC", "").getSeq(),
        new Card("AC", "").getSeq())
    );
    assertEquals(PokerHand.ROYAL_STRAIGHT, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void royalFlushTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("0C", "").getSeq(),
        new Card("JC", "").getSeq(),
        new Card("QC", "").getSeq(),
        new Card("KC", "").getSeq(),
        new Card("AC", "").getSeq())
    );
    assertEquals(PokerHand.ROYAL_FLUSH, new ScoreHand(cardSeqList).getRank());
  }

  @Test
  void flushTest() {
    List<Integer> cardSeqList = new ArrayList<Integer>(
      Arrays.asList(
        new Card("2D", "").getSeq(),
        new Card("4D", "").getSeq(),
        new Card("5D", "").getSeq(),
        new Card("7D", "").getSeq(),
        new Card("AD", "").getSeq())
    );
    assertEquals(PokerHand.FLUSH, new ScoreHand(cardSeqList).getRank());
  }


}

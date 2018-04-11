package com.javafxpert.carddeckdemo.util.poker;

import java.util.Arrays;
import java.util.List;

/**
 * Score a hand of poker
 * for 5 cards, no wild cards.
 * @author /u/Philboyd_Studge on 3/26/2016.
 */
public class ScoreHand {

    // constants for evaluating pairs
    public static final int ONE_PAIR = 7;
    public static final int TWO_PAIR = 9;
    public static final int THREE_OF_A_KIND = 11;
    public static final int FULL_HOUSE = 13;
    public static final int FOUR_OF_A_KIND = 17;

    List<Integer> hand;                 // the hand
    int[] faceFrequency = new int[13];  // frequency table for face values
    int[] suitFrequency = new int[4];   // frequency table for suits
    boolean hasAce;                     // hand contains at least one Ace
    boolean isRoyal;                    // hand contains a straight that is 'royal' i.e. A-10-J-Q-K
    PokerHand rank;                     // calculated rank of the hand
    int highCard;                       // highest card in hand for tiebreakers

    /**
     * Constructor
     * @param hand list of integers in range 0 - 51, must have only 5 elements
     */
    public ScoreHand(List<Integer> hand) {
        if (hand.size() != 5) {
            throw new IllegalArgumentException("Hand incorrect size");
        }
        this.hand = hand;
        getFrequencies();
        rank = PokerHand.HIGH_CARD;
        findHighCard();
        rankHand();
    }

    public PokerHand getRank() {
        return rank;
    }

    public int getHighCard() {
        return highCard;
    }

    private void rankHand() {

        // find all possibilities of straights first
        if (isStraight()) {
            if (isFlush()) {
                rank = isRoyal ? PokerHand.ROYAL_FLUSH : PokerHand.STRAIGHT_FLUSH;
            } else {
                rank = isRoyal ? PokerHand.ROYAL_STRAIGHT : PokerHand.STRAIGHT;
            }
        } else {
            if (isFlush()) rank = PokerHand.FLUSH;
        }

        // now find pairs/other multiples
        int pairs = getPairSum();
        switch (pairs) {
            case ONE_PAIR:
                if (rank.compareTo(PokerHand.ONE_PAIR) < 0) rank = PokerHand.ONE_PAIR;
                break;
            case TWO_PAIR:
                if (rank.compareTo(PokerHand.TWO_PAIR) < 0) rank = PokerHand.TWO_PAIR;
                break;
            case THREE_OF_A_KIND:
                if (rank.compareTo(PokerHand.THREE_OF_A_KIND) < 0) rank = PokerHand.THREE_OF_A_KIND;
                break;
            case FULL_HOUSE:
                if (rank.compareTo(PokerHand.FULL_HOUSE) < 0) rank = PokerHand.FULL_HOUSE;
                break;
            case FOUR_OF_A_KIND:
                if (rank.compareTo(PokerHand.FOUR_OF_A_KIND) < 0) rank = PokerHand.FOUR_OF_A_KIND;
                break;
            default:

        }
    }

    /**
     * fill frequency tables with hand data
     */
    private void getFrequencies() {
        for (int each : hand) {

            // kill three birds with one for-loop
            if (Cards.getFaceValue(each) == 0) hasAce = true;
            faceFrequency[Cards.getFaceValue(each)]++;
            suitFrequency[Cards.getSuit(each)]++;
        }
    }

    /**
     * use frequency of suits table to find a flush
     * @return true if all cards the same suit
     */
    private boolean isFlush() {
        for (int each : suitFrequency) {
            if (each == 5) return true;
        }
        return false;
    }

    /**
     * get a sorted int array for use in finding straights
     * @return sorted array of card face values
     */
    private int[] getSortedArray() {
        int[] sorted = new int[5];
        int i = 0;
        for (int each : hand) {
            sorted[i++] = Cards.getFaceValue(each);
        }
        Arrays.sort(sorted);
        return sorted;
    }

    /**
     * check for straights
     * @return true if a 5-card run exists
     */
    private boolean isStraight() {
        int[] sorted = getSortedArray();

        // ugly but why not
        if (hasAce && sorted[1] > 1) {
            if (sorted[1]==9 && sorted[2]==10 &&
                sorted[3]==11 && sorted[4]== 12) {
                isRoyal = true;
                return true;
            }
        } else {
            for (int i = 1; i < 5; i++) {
                if (sorted[i] - sorted[i - 1] != 1) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * use the face value frequency table to get a
     * unique number for the different possibilities
     * @return pair sum number that will correspond to the constants above
     */
    private int getPairSum() {
        int sum = 0;
        for (int each : faceFrequency) {
            sum += each * each;
        }
        return sum;
    }

    /**
     * find and set the high card for tiebreaker purposes
     */
    private void findHighCard() {
        if (hasAce) {
            for (int each : hand) {
                if (Cards.getFaceValue(each) == 0) highCard = each;
            }
        } else {
            int max = -1;
            for (int each : hand) {
                if (Cards.getFaceValue(each) > max) {
                    max = Cards.getFaceValue(each);
                    highCard = each;
                }
            }
        }

    }

}

package com.javafxpert.carddeckdemo.poker;

import java.util.*;

/**
 * Cards class for playing cards. Uses simple integer-based system
 * where the card face value is n mod 13 and the suit is n mod 4
 * @author /u/Philboyd_Studge on 3/26/2016.
 */
public class Cards {
    private static final int DECK_SIZE = 52;
    private static final int FACES = 13;
    private static final String[] FACE_NAMES = { "Ace", "Two", "Three", "Four",
                                                "Five", "Six", "Seven", "Eight",
                                                "Nine", "Ten", "Jack", "Queen", "King"};
    private static final String[] SUIT_NAMES = { "Clubs", "Hearts", "Spades", "Diamonds"};

    Random rand = new Random();

    private int[] deck;     // deck itself
    private int pointer;    // current position in deck


    /**
     * Constructor
     */
    public Cards() {
        deck = new int[DECK_SIZE];

        // initialize deck to values 0 - 51
        for (int i = 0; i < DECK_SIZE; i++) {
            deck[i] = i;
        }
    }

    /**
     * Get card integer value at position n
     * @param n position in deck
     * @return integer card value
     */
    public int getCard(int n) {
        return deck[n];
    }

    /**
     * Fisher-yates shuffle
     */
    public void shuffle() {
        pointer = 0;
        for (int i = DECK_SIZE - 1; i > 0; i--) {
            int j = rand.nextInt(i);
            if (j != i) {
                swap(j, i);
            }
        }
    }

    private void swap(int first, int second) {
        int temp = deck[first];
        deck[first] = deck[second];
        deck[second] = temp;
    }

    /**
     * Get face value 0 - 12 for card
     * note: off by one compared to actual card face
     * @param card integer value of card
     * @return face value
     */
    public static int getFaceValue(int card) {
        return card % FACES;
    }

    /**
     * Get suit 0 - 3 for card
     * '& 3' a bitwise way of saying '% 4'
     * @param card integer value of card
     * @return suit
     */
    public static int getSuit(int card) {
        return card & 3;
    }

    /**
     * Get string representation of card
     * @param card integer value of card
     * @return string of card name
     */
    public static String getCardName(int card) {
        return FACE_NAMES[getFaceValue(card)] + " of " + SUIT_NAMES[getSuit(card)];
    }

    /**
     * Deal n cards from the deck
     * re-shuffles if not enough cards left for a full hand
     * @param n number of cards to deal
     * @return Linked list of integers representing the hand
     */
    public List<Integer> deal(int n) {
        if (n < 1) return null;

        // don't reshuffle in the middle of a hand
        if (DECK_SIZE - pointer < n) shuffle();


        List<Integer> hand = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            hand.add(dealCard());
        }
        return hand;
    }

    /**
     * deal single card and increment pointer
     * @return
     */
    private int dealCard() {
        return deck[pointer++];
    }


}

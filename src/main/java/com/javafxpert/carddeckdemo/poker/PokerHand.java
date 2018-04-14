package com.javafxpert.carddeckdemo.poker;

/**
 * enum for ranking a poker hand
 * @author /u/Philboyd_Studge on 3/26/2016.
 */
public enum PokerHand {
    HIGH_CARD("High Card"),
    ONE_PAIR("One Pair"),
    TWO_PAIR("Two Pairs"),
    THREE_OF_A_KIND("Three of a Kind"),
    STRAIGHT("Straight"),
    ROYAL_STRAIGHT("Royal Straight"),
    FLUSH("Flush"),
    FULL_HOUSE("Full House"),
    FOUR_OF_A_KIND("Four of a Kind"),
    STRAIGHT_FLUSH("Straight Flush"),
    ROYAL_FLUSH("Royal Flush");

    private String name;

    PokerHand(String name) {
        this.name = name;
    }

    public String getName() { return name; }
}

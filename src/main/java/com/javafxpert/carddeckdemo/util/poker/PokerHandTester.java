package com.javafxpert.carddeckdemo.util.poker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author /u/Philboyd_Studge on 3/27/2016.
 */
public class PokerHandTester {
    public static void main(String[] args) {
        Cards deck = new Cards();
        deck.shuffle();

        Map<PokerHand, Integer> frequencyMap = new HashMap<>();
        int total = 1000000;

        for (int i = 0; i < total; i++) {
            List<Integer> hand = deck.deal(5);

            PokerHand temp = new ScoreHand(hand).getRank();
            frequencyMap.put(temp, frequencyMap.getOrDefault(temp, 0) + 1);
        }

        for (PokerHand each : PokerHand.values()) {
            int count = frequencyMap.getOrDefault(each, 0);
            System.out.println(each.getName() + " : " + count);
            System.out.printf("%.4f%%%n", (( count / (double) total) * 100));
        }
    }
}

package com.javafxpert.carddeckdemo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javafxpert.carddeckdemo.poker.Cards;
import com.javafxpert.carddeckdemo.poker.domain.PokerHand;
import com.javafxpert.carddeckdemo.poker.domain.ScoreHand;
import org.junit.Test;

/**
 * @author /u/Philboyd_Studge on 3/27/2016.
 */
public class PokerHandTester {

    @Test
    public void test(String[] args) {
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

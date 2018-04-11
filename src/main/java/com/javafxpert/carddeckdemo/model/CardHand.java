package com.javafxpert.carddeckdemo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class CardHand {

	private final List<Card> cards;
	private final String     name;

	@JsonCreator
	public CardHand(List<Card> cards, String name) {
		this.cards = new ArrayList<>(cards);
		this.name = name;
	}

	public List<Card> getCards() {
		return cards;
	}

	public String getName() {
		return name;
	}
}
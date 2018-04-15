package com.javafxpert.carddeckdemo.deck.repository;

import com.javafxpert.carddeckdemo.deck.domain.Card;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardDeckRepository extends ReactiveMongoRepository<Card, String> {
}

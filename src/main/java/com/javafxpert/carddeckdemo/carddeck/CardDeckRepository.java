package com.javafxpert.carddeckdemo.carddeck;

import com.javafxpert.carddeckdemo.carddeck.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardDeckRepository extends ReactiveMongoRepository<Card, String> {
}

package com.javafxpert.carddeckdemo.repository;

import com.javafxpert.carddeckdemo.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CardDeckRepository extends ReactiveMongoRepository<Card, String> {
}

package com.javafxpert.carddeckdemo.poker;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HandFrequencyRepository extends ReactiveMongoRepository<HandFrequency, String> {
}

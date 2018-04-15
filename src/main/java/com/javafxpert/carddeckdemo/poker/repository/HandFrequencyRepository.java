package com.javafxpert.carddeckdemo.poker.repository;

import com.javafxpert.carddeckdemo.poker.domain.HandFrequency;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HandFrequencyRepository extends ReactiveMongoRepository<HandFrequency, String> {
}

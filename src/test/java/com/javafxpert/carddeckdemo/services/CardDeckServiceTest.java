package com.javafxpert.carddeckdemo.services;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.controllers.CardDeckController;
import com.javafxpert.carddeckdemo.model.Card;
import com.javafxpert.carddeckdemo.repository.CardDeckRepository;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckServiceTest {
  CardDeckDemoProperties cardDeckDemoProperties = new CardDeckDemoProperties();
  CardDeckRepository cardDeckRepository = new CardDeckRepository() {
    @Override
    public <S extends Card> Mono<S> insert(S s) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> insert(Iterable<S> iterable) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> insert(Publisher<S> publisher) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> findAll(Example<S> example) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> findAll(Example<S> example, Sort sort) {
      return null;
    }

    @Override
    public <S extends Card> Mono<S> findOne(Example<S> example) {
      return null;
    }

    @Override
    public <S extends Card> Mono<Long> count(Example<S> example) {
      return null;
    }

    @Override
    public <S extends Card> Mono<Boolean> exists(Example<S> example) {
      return null;
    }

    @Override
    public Flux<Card> findAll(Sort sort) {
      return null;
    }

    @Override
    public <S extends Card> Mono<S> save(S s) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> saveAll(Iterable<S> iterable) {
      return null;
    }

    @Override
    public <S extends Card> Flux<S> saveAll(Publisher<S> publisher) {
      return null;
    }

    @Override
    public Mono<Card> findById(String s) {
      return null;
    }

    @Override
    public Mono<Card> findById(Publisher<String> publisher) {
      return null;
    }

    @Override
    public Mono<Boolean> existsById(String s) {
      return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> publisher) {
      return null;
    }

    @Override
    public Flux<Card> findAll() {
      return null;
    }

    @Override
    public Flux<Card> findAllById(Iterable<String> iterable) {
      return null;
    }

    @Override
    public Flux<Card> findAllById(Publisher<String> publisher) {
      return null;
    }

    @Override
    public Mono<Long> count() {
      return null;
    }

    @Override
    public Mono<Void> deleteById(String s) {
      return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> publisher) {
      return null;
    }

    @Override
    public Mono<Void> delete(Card card) {
      return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends Card> iterable) {
      return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends Card> publisher) {
      return null;
    }

    @Override
    public Mono<Void> deleteAll() {
      return null;
    }
  };
  CardDeckService cardDeckService = new CardDeckService(cardDeckDemoProperties,
    cardDeckRepository);

  @Test
  void createStringFromCardFlux() {
    Flux<Card> cardFlux = Flux.just(
      new Card("AC", ""),
      new Card("2C", ""),
      new Card("3C", ""),
      new Card("4C", ""),
      new Card("5C", "")
    );

    StepVerifier.create(cardDeckService.createStringFromCardFlux(cardFlux))
      .expectNext("AC,2C,3C,4C,5C");
  }
}

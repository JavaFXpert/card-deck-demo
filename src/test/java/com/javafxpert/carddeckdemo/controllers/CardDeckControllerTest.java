package com.javafxpert.carddeckdemo.controllers;

import com.javafxpert.carddeckdemo.deck.configuration.CardDeckImagesServerProperties;
import com.javafxpert.carddeckdemo.deck.controller.CardDeckController;
import com.javafxpert.carddeckdemo.deck.domain.Card;
import com.javafxpert.carddeckdemo.deck.repository.CardDeckRepository;
import com.javafxpert.carddeckdemo.deck.service.impl.DefaultCardDeckService;
import com.javafxpert.carddeckdemo.deck.service.impl.ProxyPokerService;
import org.junit.jupiter.api.Test;

import org.reactivestreams.Publisher;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class CardDeckControllerTest {
  CardDeckImagesServerProperties
                     cardDeckImagesServerProperties = new CardDeckImagesServerProperties();
  CardDeckRepository cardDeckRepository             = new CardDeckRepository() {
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
//  CardDeckController cardDeckController             = new CardDeckController(new DefaultCardDeckService(
//          cardDeckImagesServerProperties, cardDeckRepository),
//		  new ProxyPokerService(new Po),
//		  cardDeckImagesServerProperties);

  /* TODO: Fix this text to accommodate that a Mono<CardHand> is now returned
  @Test
  void getCardDeckRiffleShuffle() {
    String cardsStrA = "AS,2S,3S,4S,5S,AH,2H,3H,4H,5H";
    StepVerifier.create(
      cardDeckController.getCardDeckRiffleShuffle(cardsStrA))
      .expectNext(new Card("AS", "null:null/images"))
      .expectNext(new Card("AH", "null:null/images"))
      .expectNext(new Card("2S", "null:null/images"))
      .expectNext(new Card("2H", "null:null/images"))
      .expectNext(new Card("3S", "null:null/images"))
      .expectNext(new Card("3H", "null:null/images"))
      .expectNext(new Card("4S", "null:null/images"))
      .expectNext(new Card("4H", "null:null/images"))
      .expectNext(new Card("5S", "null:null/images"))
      .expectNext(new Card("5H", "null:null/images"))
      .expectComplete()
      .verify();
  }
  */


//  @Test
//  void retrievePokerHandName() {
//    cardDeckImagesServerProperties.setCardimageshost("http://127.0.0.1");
//    cardDeckImagesServerProperties.setCardimagesport("8080");
//    Flux<Card> cardFlux = Flux.just(
//      new Card("AC", ""),
//      new Card("2C", ""),
//      new Card("3C", ""),
//      new Card("4C", ""),
//      new Card("5C", "")
//    );
//
////    StepVerifier.create(cardDeckController.(cardFlux))
////      .expectNext("Straight Flush")
////      .verifyComplete();
//    //assertEquals("Straight Flush", cardDeckController.handNameFromDeck(cardFlux));
//  }
}

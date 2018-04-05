/*
package com.javafxpert.carddeckdemo.repository;

import com.javafxpert.carddeckdemo.CardDeckDemoProperties;
import com.javafxpert.carddeckdemo.model.Card;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;


@Component
public class DataLoader {
  private final CardDeckRepository cardDeckRepository;
  private final CardDeckDemoProperties cardDeckDemoProperties;
  private String imagesUri = "";

  public DataLoader(CardDeckRepository cardDeckRepository,
                    CardDeckDemoProperties cardDeckDemoProperties) {
    this.cardDeckRepository = cardDeckRepository;
    this.cardDeckDemoProperties = cardDeckDemoProperties;
    imagesUri = cardDeckDemoProperties.getCardimageshost() + ":" + cardDeckDemoProperties.getCardimagesport() + "/images";
  }

  @PostConstruct
  private void loadData() {
    this.cardDeckRepository.deleteAll().thenMany(
      Flux.just(
        new Card("AS", imagesUri),
        new Card("2S", imagesUri),
        new Card("3S", imagesUri),
        new Card("4S", imagesUri),
        new Card("5S", imagesUri),
        new Card("6S", imagesUri),
        new Card("7S", imagesUri),
        new Card("8S", imagesUri),
        new Card("9S", imagesUri),
        new Card("0S", imagesUri),
        new Card("JS", imagesUri),
        new Card("QS", imagesUri),
        new Card("KS", imagesUri),

        new Card("AD", imagesUri),
        new Card("2D", imagesUri),
        new Card("3D", imagesUri),
        new Card("4D", imagesUri),
        new Card("5D", imagesUri),
        new Card("6D", imagesUri),
        new Card("7D", imagesUri),
        new Card("8D", imagesUri),
        new Card("9D", imagesUri),
        new Card("0D", imagesUri),
        new Card("JD", imagesUri),
        new Card("QD", imagesUri),
        new Card("KD", imagesUri),

        new Card("AC", imagesUri),
        new Card("2C", imagesUri),
        new Card("3C", imagesUri),
        new Card("4C", imagesUri),
        new Card("5C", imagesUri),
        new Card("6C", imagesUri),
        new Card("7C", imagesUri),
        new Card("8C", imagesUri),
        new Card("9C", imagesUri),
        new Card("0C", imagesUri),
        new Card("JC", imagesUri),
        new Card("QC", imagesUri),
        new Card("KC", imagesUri),

        new Card("AH", imagesUri),
        new Card("2H", imagesUri),
        new Card("3H", imagesUri),
        new Card("4H", imagesUri),
        new Card("5H", imagesUri),
        new Card("6H", imagesUri),
        new Card("7H", imagesUri),
        new Card("8H", imagesUri),
        new Card("9H", imagesUri),
        new Card("0H", imagesUri),
        new Card("JH", imagesUri),
        new Card("QH", imagesUri),
        new Card("KH", imagesUri))
      .flatMap(this.cardDeckRepository::save))
      .subscribe(System.out::println);

  }
}
*/

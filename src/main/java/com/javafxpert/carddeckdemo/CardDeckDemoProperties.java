package com.javafxpert.carddeckdemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("carddeckdemo")
@Component
public class CardDeckDemoProperties {
  public String cardimageshost;
  public String cardimagesport;

  public String getCardimageshost() {
    return cardimageshost;
  }

  public void setCardimageshost(String cardimageshost) {
    this.cardimageshost = cardimageshost;
  }

  public String getCardimagesport() {
    return cardimagesport;
  }

  public void setCardimagesport(String cardimagesport) {
    this.cardimagesport = cardimagesport;
  }
}

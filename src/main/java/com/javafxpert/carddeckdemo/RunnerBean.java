package com.javafxpert.carddeckdemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RunnerBean implements CommandLineRunner {
  @Override
  public void run(String... args) throws Exception {
    System.out.println("After starting up");

  }
}

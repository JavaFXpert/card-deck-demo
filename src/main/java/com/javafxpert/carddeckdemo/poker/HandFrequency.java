package com.javafxpert.carddeckdemo.poker;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class HandFrequency {
  @Id
  private String id;

  private String handName;
  private long frequency;
  private double frequencyPercent;

  public HandFrequency(String handName, long frequency) {
    this.handName = handName;
    this.frequency = frequency;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getHandName() {
    return handName;
  }

  public void setHandName(String handName) {
    this.handName = handName;
  }

  public long getFrequency() {
    return frequency;
  }

  public void setFrequency(long frequency) {
    this.frequency = frequency;
  }

  public double getFrequencyPercent() {
    return frequencyPercent;
  }

  public void setFrequencyPercent(double frequencyPercent) {
    this.frequencyPercent = frequencyPercent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    HandFrequency that = (HandFrequency) o;
    return frequency == that.frequency &&
      Double.compare(that.frequencyPercent, frequencyPercent) == 0 &&
      Objects.equals(id, that.id) &&
      Objects.equals(handName, that.handName);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, handName, frequency, frequencyPercent);
  }

  @Override
  public String toString() {
    return "HandFrequency{" +
      "id='" + id + '\'' +
      ", handName='" + handName + '\'' +
      ", frequency=" + frequency +
      ", frequencyPercent=" + frequencyPercent +
      '}';
  }
}

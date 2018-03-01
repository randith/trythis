package io.awesome.model;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class CorId {
  private final String correlationId;

  public CorId() {
    this.correlationId = String.format("%016x", ThreadLocalRandom.current().nextLong());
  }

  public String get() {
    return correlationId;
  }

  @Override
  public String toString() {
    return "[" + correlationId + "]";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CorId that = (CorId) o;
    return Objects.equals(correlationId, that.correlationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(correlationId);
  }
}

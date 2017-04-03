package ar.edu.itba.sia.gps.strategy;

import java.util.Random;

public abstract class RandomOrder {
  private static final int POSITIVE = 1;
  private static final int NEGATIVE = -1;
  private static final Random rand = new Random();

  public static int randomOrder() {
    return rand.nextBoolean() ? POSITIVE : NEGATIVE;
  }
}

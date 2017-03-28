package ar.edu.itba.sia;

import java.util.stream.IntStream;

public class OwnTests {

  public static void main(String[] args) {
    final int[] counter = {0};
    System.out.println(IntStream.range(0, 10).reduce(0, (mem, currentValue) -> {
      System.out.printf("Left: %d; Right %d\n", mem, currentValue);
      counter[0]++;
      return mem + currentValue;
    }));
    System.out.println(counter[0]);
  }
}
